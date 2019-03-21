package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;

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
    private ScrollPane badgePane;

    private User user;

    boolean userNameChanged = false;

    public void initialize() {
        modDataButton.setVisible(false);
        modDataButton.setDisable(true);

        updateUserValues();
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
            //API.updateUser();
            API.requestFakeStatus(new ServerCallback<Object, BasicResponse>() {

                @Override
                public void run() {
                    if (getStatusCode() != 200) {
                        errorMsg.setTextFill(Color.RED);
                        errorMsg.setText("Error while changing username. Statuscode:" + getStatusCode());
                    } else {
                        errorMsg.setTextFill(Color.GREEN);
                        errorMsg.setText("Username succesfully changed");
                        updateUserValues();
                    }
                }
            });
        }
        userForm.setText("");
    }

    private void updateUserValues() {
        API.retrieveFakeUser(new ServerCallback<Object, User>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    user = getResult();
                    userTitle.setText(user.getName() + "'s Account");
                    userForm.setPromptText(user.getName());
                    co2Savings.setText(user.getPoints().toString() + " Points");
                }
            }
        });
    }
}
