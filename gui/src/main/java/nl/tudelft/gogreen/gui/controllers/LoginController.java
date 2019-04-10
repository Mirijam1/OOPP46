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
            API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
                @Override
                public void run() {
                    if (getStatusCode() == StatusCodes.AUTHENTICATED) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sidebar.fxml"));

                                // get a handle to the stage
                                Stage current = (Stage) loginbtn.getScene().getWindow();
                                try {
                                    root = loader.load();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Stage stage = new Stage();
                                stage.setTitle("GOGREEN - overview");
                                stage.getIcons().add(new Image("img/leaficon.png"));
                                stage.setScene(new Scene(root, 1380, 720));
                                stage.show();
                                stage.setOnCloseRequest(t -> {
                                    API.closeAPI();
                                    Platform.exit();
                                    System.exit(0);
                                });

                                // do what you have to do
                                SidebarController.controller = loader.getController();
                                current.close();
                            }
                        });
                    } else {
                        System.out.println("Invalid username or password");
                        Platform.runLater(() -> pwdmessage.setText("Invalid username or password"));
                    }
                }
            }, new User(userfield.getText(), passfield.getText(), null));
        }
    }

    private void changeStyleError(TextField field) {
        field.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 5px;");
    }

    @FXML
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ActionEvent ae = new ActionEvent();
            login(ae);
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        try {
            Parent root;
            Stage current = (Stage) loginbtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/SignUpScreen.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("GOGREEN - Sign Up");
            stage.getIcons().add(new Image("img/leaficon.png"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            stage.setOnCloseRequest(t -> {
                API.closeAPI();
                Platform.exit();
                System.exit(0);
            });

            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


