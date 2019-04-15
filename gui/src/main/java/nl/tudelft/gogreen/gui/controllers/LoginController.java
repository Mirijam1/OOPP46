package nl.tudelft.gogreen.gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.gui.controllers.verification.InputController;
import nl.tudelft.gogreen.gui.controllers.verification.IntegerAsStringConverter;
import nl.tudelft.gogreen.shared.StatusCodes;
import nl.tudelft.gogreen.shared.models.User;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginbtn;

    @FXML
    private Button signupbtn;

    @FXML
    private PasswordField passfield;

    @FXML
    private TextField userfield;

    @FXML
    private Label pwdmessage;

    @FXML
    private Label usernamemessage;

    @FXML
    void login(ActionEvent event) {
        passfield.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        userfield.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        usernamemessage.setText("");
        pwdmessage.setText("");

        if (userfield.getText().length() < 3 && (passfield.getText().length() < 5)) {
            System.out.println("no username and password");
            usernamemessage.setText("Please enter username");
            pwdmessage.setText("Please enter password");
            changeStyleError(passfield);
            changeStyleError(userfield);
        } else if (userfield.getText().length() < 3 && passfield.getText().length() >= 5) {
            System.out.println("no valid username");
            usernamemessage.setText("Please enter username");
            changeStyleError(userfield);
        } else if (userfield.getText().length() >= 3 && passfield.getText().length() < 5) {
            System.out.println("no valid password");
            pwdmessage.setText("Please enter password");
            changeStyleError(passfield);
        } else {
            this.attemptInitialAuthentication(userfield.getText(), passfield.getText());
        }
    }

    private void attemptInitialAuthentication(String username, String password) {
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getStatusCode() == StatusCodes.AUTHENTICATED) {
                        continueAfterAuthentication();
                    } else if (getResult().getAdditionalInfo() != null
                            && getResult().getAdditionalInfo().equals("2fa")) {
                        attemptAuthenticationWithTwoFactorAuthentication(username, password, false);
                    } else {
                        pwdmessage.setText("Invalid username or password");
                    }
                });

            }
        }, new User(userfield.getText(), passfield.getText(), null), null);
    }

    private void attemptAuthenticationWithTwoFactorAuthentication(String username, String password, boolean error) {
        InputController<String, IntegerAsStringConverter> input = new InputController<>("Enter your 2FA token",
                "Enter the 2FA token from the app",
                error ? "Incorrect token and/or credentials" : null,
                new IntegerAsStringConverter());

        input.openScreen();

        if (input.inputReceived()) {
            this.loginWithToken(username, password, input.getResult());
        }
    }

    private void loginWithToken(String username, String password, String token) {
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getStatusCode() == StatusCodes.UNAUTHORIZED) {
                        attemptAuthenticationWithTwoFactorAuthentication(username, password, true);
                    } else {
                        continueAfterAuthentication();
                    }
                });
            }
        }, new User(username, password, null), token);
    }

    private void continueAfterAuthentication() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sidebar.fxml"));

        // get a handle to the stage
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("GOGREEN - overview");
        stage.getIcons().add(new Image("img/leaficon.png"));
        stage.setScene(new Scene(root, 1380, 720));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        stage.setOnCloseRequest(t -> {
            API.closeAPI();
            Platform.exit();
            System.exit(0);
        });

        // do what you have to do
        Stage current = (Stage) loginbtn.getScene().getWindow();
        SidebarController.controller = loader.getController();
        current.close();
    }

    private void changeStyleError(TextField field) {
        field.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 5px;");
    }

    @FXML
    protected void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ActionEvent ae = new ActionEvent();
            login(ae);
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SignUpScreen.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("GOGREEN - Sign Up");
            stage.getIcons().add(new Image("img/leaficon.png"));
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
            stage.setOnCloseRequest(t -> {
                API.closeAPI();
                Platform.exit();
                System.exit(0);
            });

            Stage current = (Stage) loginbtn.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


