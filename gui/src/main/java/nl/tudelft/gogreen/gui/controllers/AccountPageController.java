package nl.tudelft.gogreen.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.AchievedBadge;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.gui.controllers.helpers.TwoFactorEnableController;
import nl.tudelft.gogreen.gui.controllers.verification.IntegerConverter;
import nl.tudelft.gogreen.shared.models.Badge;
import nl.tudelft.gogreen.shared.models.User;
import nl.tudelft.gogreen.shared.models.UserServer;

import java.util.ArrayList;

public class AccountPageController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label userTitle;

    @FXML
    private Label co2Savings;

    @FXML
    private Label errorMsg;

    @FXML
    private Button modDataButton;

    @FXML
    private JFXTextField userForm;

    @FXML
    private GridPane badges;

    @FXML
    private JFXToggleButton twoFactorToggleButton;

    private User user;
    private Float points;
    private Badge badge;

    private boolean userNameChanged = false;

    public void initialize() {
        modDataButton.setVisible(false);
        modDataButton.setDisable(true);

        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {

                if (getStatusCode() != 200) {
                    System.out.println("Error");
                } else {
                    Platform.runLater(() -> updateUserValues(getResult().getUser(), getResult().getPoints()));
                }
            }
        });

        API.retrieveAchievedBadges(new ServerCallback<Object, AchievedBadge[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initBadges(getResult()));
            }
        });
    }

    @FXML
    protected void checkSubmittable() {
        String un = userForm.getText();
        boolean isDisabled = un.isEmpty() || un.trim().isEmpty();
        modDataButton.setDisable(isDisabled);
        if (!userNameChanged) {
            modDataButton.setVisible(true);
            FadeTransition fadeTransition
                    = new FadeTransition(Duration.millis(500), modDataButton);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
            userNameChanged = true;
        }
    }

    @FXML
    void editUsername(ActionEvent event) {
        String newUserName = userForm.getText();

        if (newUserName.length() < 3) {
            errorMsg.setTextFill(Color.RED);
            errorMsg.setText("Username must be at least 3 characters");
        } else if (user.getUsername().equals(newUserName)) {
            errorMsg.setTextFill(Color.RED);
            errorMsg.setText("Same username");
        } else {
            System.out.println("new username: " + newUserName + " and password: " + user.getPassword());
            API.updateUser(new ServerCallback<User, User>() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        updateUserValues(new User(getResult().getUsername(),
                                null, null), points);
                        System.out.println(getResult());
                    });
                }
            }, new User(userForm.getText(), user.getPassword(), user.getMail()));

        }
        userForm.setText("");
    }

    private void updateUserValues(User newuser, Float newpoints) {
        user = newuser;
        points = newpoints;
        String pointText = " Points";

        if (points <= 1f) {
            pointText = " Point";
        }

        userTitle.setText(user.getUsername() + "'s Account");
        userForm.setPromptText(user.getUsername());
        SidebarController.controller.userLabel.setText(user.getUsername());
        co2Savings.setText(points.toString() + pointText);

        twoFactorToggleButton.setSelected(user.isTfaEnabled());
    }

    public void initBadges(AchievedBadge[] achievedBadges) {
        String badgeName;
        int imageCol = 0;
        int imageRow = 0;
        //keeps track of achieved badges
        ArrayList<AchievedBadge> addedBadges = new ArrayList<>();

        // Loop through achieved badges
        for (AchievedBadge achievedBadge : achievedBadges) {
            addedBadges.add(achievedBadge);
            int counter = 0;

            //Counts how many times the badge has already been achieved
            for (AchievedBadge badge : addedBadges) {
                if (badge.equals(achievedBadge)) {
                    counter++;
                }
            }
            //If the badge has been achieved multiple times, it won't be shown again
            if (counter <= 1) {
                badgeName = achievedBadge.getBadge().getBadgeName().toUpperCase();

                String imgName = "img/" + badgeName + ".png";
                System.out.println(imgName);
                Image image = new Image(imgName);
                ImageView iv = new ImageView(image);

                iv.setFitWidth(150);
                iv.setFitHeight(150);
                iv.setImage(image);

                badges.add(iv, imageCol, imageRow);

                imageCol++;
                if (imageCol > 1) {
                    // Reset Column
                    imageCol = 0;
                    // Next Row
                    imageRow++;
                }
            }
        }
    }

    @FXML
    public void handleTwoFactorToggle(ActionEvent event) {
        JFXToggleButton button = (JFXToggleButton) event.getTarget();

        this.toggleTwoFactorAuthentication(button.isSelected());
    }

    private void toggleTwoFactorAuthentication(Boolean enable) {
        System.out.println("Toggling 2FA: " + enable);
        API.toggleTwoFactorAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                if (enable) {
                    Platform.runLater(() -> enableTwoFactorAuthentication(getResult().getAdditionalInfo()));
                }
            }
        }, enable);
    }

    private void enableTwoFactorAuthentication(String qrUrl) {
        TwoFactorEnableController twoFactorEnableController =
                new TwoFactorEnableController(qrUrl, new IntegerConverter());

        twoFactorEnableController.openScreen();

        if (!twoFactorEnableController.isEnabled()) {
            twoFactorToggleButton.setSelected(false);
        }
    }
}