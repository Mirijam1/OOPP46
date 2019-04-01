package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.User;
import nl.tudelft.gogreen.shared.models.UserServer;

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
    private GridPane badgePane;

    private User user;
    private Float points;

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
                    user = getResult().getUser();
                    points = getResult().getPoints();
                    Platform.runLater(() -> updateUserValues());
                }
            }
        });
        initBadges();
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
        } else if (user.getName().equals(newUserName)) {
            errorMsg.setTextFill(Color.RED);
            errorMsg.setText("Same username");
        } else {
            System.out.println("new username: " + newUserName + " and password: " + user.getPassword());
            API.updateUser(new ServerCallback<nl.tudelft.gogreen.api.servermodels.User, nl.tudelft.gogreen.api.servermodels.User>() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (getStatusCode() != 200) {
                            errorMsg.setTextFill(Color.RED);
                            errorMsg.setText("Error while changing username. Statuscode:" + getStatusCode());
                        } else {
                            errorMsg.setTextFill(Color.GREEN);
                            errorMsg.setText("Username succesfully changed");
                            user.setUsername(getResult().getUsername());
                            updateUserValues();
                        }
                    });
                }
            }, new nl.tudelft.gogreen.api.servermodels.User(newUserName, user.getPassword()));
        }
        userForm.setText("");
    }

    private void updateUserValues() {
        String pointText = " Points";

        if (points <= 1f) {
            pointText = " Point";
        }

        userTitle.setText(user.getName() + "'s Account");
        userForm.setPromptText(user.getName());
        co2Savings.setText(points.toString() + pointText);
    }

    private void initBadges() {
        String[] badgelist = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        int badgeAmount = badgelist.length;
        int counter = 0;
        int rows = (badgeAmount / 4) + 1;

        for (int i = 0; i < rows; i++) {
            badgePane.addRow(i + 1);

            for (int j = 0; j < 4; j++) {
                //badgePane.add(new Label(badgelist[counter]), j, i);


                //add gui to badge


                if (counter == badgeAmount - 1) {
                    break;
                }
                counter++;
            }
        }
    }
}