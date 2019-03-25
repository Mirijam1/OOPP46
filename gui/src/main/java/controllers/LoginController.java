package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.StatusCodes;

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
    void login(ActionEvent event) throws Exception {
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

//            try {
//                root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sidebar.fxml"));
//                Stage stage = new Stage();
//                stage.setTitle("GoGreen");
//                stage.setScene(new Scene(root, 1280, 720));
//                stage.show();
//                // Hide this current window (if this is what you want)
//                ((Node) (event.getSource())).getScene().getWindow().hide();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
                @Override
                public void run() {
                    if (getStatusCode() == StatusCodes.AUTHENTICATED) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                // get a handle to the stage
                                Stage current = (Stage) loginbtn.getScene().getWindow();
                                try {
                                    root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sidebar.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Stage stage = new Stage();
                                stage.setTitle("GOGREEN - overview");
                                stage.setScene(new Scene(root, 1380, 720));
                                stage.show();

                                // do what you have to do
                                current.close();
                            }
                        });
                    } else {
                        System.out.println("Invalid username or password");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                pwdmessage.setText("Invalid username or password");

                            }
                        });
                    }
                }
            }, new User(userfield.getText(), passfield.getText()));
        }
    }

    public void changeStyleError(TextField field) {
        field.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 5px;");
    }


    @FXML
    public void handle(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ActionEvent ae = new ActionEvent();
            login(ae);
        }
    }

    @FXML
    void signUp(ActionEvent event) throws Exception {
        try {
            Parent root;
            Stage current = (Stage) loginbtn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SignUpScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("GOGREEN - Sign Up");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            // do what you have to do
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


