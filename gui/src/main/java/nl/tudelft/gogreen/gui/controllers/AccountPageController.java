package nl.tudelft.gogreen.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.AchievedBadge;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.gui.controllers.helpers.TwoFactorEnableController;
import nl.tudelft.gogreen.gui.controllers.verification.IntegerConverter;
import nl.tudelft.gogreen.shared.models.CompletedAchievements;
import nl.tudelft.gogreen.shared.models.User;
import nl.tudelft.gogreen.shared.models.UserServer;

import java.util.ArrayList;

import static nl.tudelft.gogreen.shared.Shared.roundval;

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
    private ScrollPane achievementScroll;

    @FXML
    private VBox achievementsVBox;

    @FXML
    private GridPane badges;

    @FXML
    private JFXToggleButton twoFactorToggleButton;

    private User user;
    private Double points;

    private boolean userNameChanged = false;

    @FXML
    protected void initialize() {
        modDataButton.setVisible(false);
        modDataButton.setDisable(true);

        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("Error");
                } else {
                    Platform.runLater(() -> updateUserValues(getResult().getUser(), (double)getResult().getPoints()));
                }
            }
        });

        API.retrieveAchievedBadges(new ServerCallback<Object, AchievedBadge[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initBadges(getResult()));
                System.out.println();
                for (int i = 0; i < getResult().length; i++) {
                    System.out.println(getResult()[i]);
                }
            }
        });

        API.retrieveAchievements(new ServerCallback<Object, CompletedAchievements[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initAchievements(getResult()));
                System.out.println();
                for (int i = 0; i < getResult().length; i++) {
                    System.out.println(getResult()[i]);
                }
            }
        });
    }

    @FXML
    protected void displayButton() {
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
    protected void editUsername(ActionEvent event) {
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
                                null, null), (double) points);
                        System.out.println(getResult());
                    });
                }
            }, new User(userForm.getText(), user.getPassword(), user.getMail()));

        }
        userForm.setText("");
    }

    private void updateUserValues(User newuser, Double newpoints) {
        user = newuser;
        points = roundval(newpoints);

        userTitle.setText(user.getUsername() + "'s Account");
        userForm.setPromptText(user.getUsername());
        SidebarController.controller.userLabel.setText(user.getUsername());
        co2Savings.setText(points.toString() + " Points");

        twoFactorToggleButton.setSelected(user.isTfaEnabled());
    }

    private void initBadges(AchievedBadge[] achievedBadges) {
        String badgeName;
        int imageCol = 0;
        int imageRow = 0;
        //keeps track of achieved badges
        ArrayList<AchievedBadge> addedBadges = new ArrayList<>();

        if (achievedBadges.length == 0) {
            Label messageText = new Label("No badges yet!");
            messageText.setStyle("-fx-background-radius: 30;"
                    + " -fx-background-color: rgba(255, 255, 255, 1.0); -fx-padding: 10");
            badges.add(messageText, 0, 0);
            return;
        }
        // Loop through achieved badges
        for (int i = 0; i < achievedBadges.length; i++) {
            AchievedBadge achievedBadge = achievedBadges[i];
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

                iv.setFitHeight(150);
                iv.setPreserveRatio(true);
                iv.setImage(image);

                badges.add(iv, imageCol, imageRow);

                imageCol++;
                if (imageCol > 1) {
                    // Reset Column
                    imageCol = 0;
                    // Next Row
                    imageRow++;

                    new RowConstraints(160);
                    badges.setPrefHeight(160 * (i + 1));
                }
            }
        }
    }

    private void initAchievements(CompletedAchievements[] achievedAchievements) {
        int entryHeight = 120;
        achievementScroll.setFitToWidth(true);
        achievementsVBox.setSpacing(6);
        achievementsVBox.setPadding(new Insets(5, 0, 0, 0));

        if (achievedAchievements.length > 0) {
            for (int i = 0; i < achievedAchievements.length; i++) {
                String imgName = "img/" + achievedAchievements[i].getAchievement().getAchievementName() + ".png";
                System.out.println(imgName);
                Image image = new Image(imgName);
                ImageView iv = new ImageView(image);
                iv.setFitHeight(100);
                iv.setPreserveRatio(true);
                iv.setTranslateX(20);
                iv.setTranslateY(5);

                Label achievementTitle = new Label(achievedAchievements[i].getAchievement().getAchievementName());
                achievementTitle.setTranslateX(140);
                achievementTitle.setTranslateY(20);

                Label achievementDesc = new Label(achievedAchievements[i].getAchievement().getDescription());
                achievementDesc.setTranslateX(140);
                achievementDesc.setTranslateY(60);

                Pane achievedEntry = new Pane();
                achievedEntry.setPrefSize(492, entryHeight);
                achievedEntry.setMaxWidth(492);
                achievedEntry.setMaxHeight(entryHeight);
                achievedEntry.setStyle("-fx-background-radius: 60; -fx-background-color: rgba(255, 255, 255, 1.0);");

                achievedEntry.getChildren().addAll(iv, achievementTitle, achievementDesc);
                achievementsVBox.setPrefHeight((entryHeight + 6) * (i + 1));
                achievementsVBox.getChildren().add(achievedEntry);
            }
        } else {
            Label messageText = new Label("Complete three activities to earn your first achievement!");
            achievementsVBox.getChildren().add(messageText);
            messageText.setStyle("-fx-background-radius: 30;"
                    + " -fx-background-color: rgba(255, 255, 255, 1.0); -fx-padding: 10");
        }
    }

    @FXML
    protected void handleTwoFactorToggle(ActionEvent event) {
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