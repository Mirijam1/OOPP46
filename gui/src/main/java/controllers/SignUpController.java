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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.StatusCodes;

import java.io.IOException;

public class SignUpController {

    @FXML
    private Button signupbtn;

    @FXML
    private PasswordField password;

    @FXML
    private Label usermessage;

    @FXML
    private Label pwdmessage;

    @FXML
    private Label message;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text properties;

    @FXML
    private TextField username;


    @FXML
    void signup(ActionEvent event) {
        password.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        username.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        usermessage.setText("");
        pwdmessage.setText("");

        if (username.getText().length() < 3 && password.getText().length() < 5) {
            System.out.println("no username and password");
            usermessage.setText("Username has to be at least 3 characters");
            pwdmessage.setText("Password has to be at least 3 characters.");
            changeStyleError(password);
            changeStyleError(username);
        } else if (username.getText().length() < 3 && password.getText().length() >= 3) {
            System.out.println("no valid username");
            usermessage.setText("Username has to be at least 3 characters");
            changeStyleError(username);
        } else if (username.getText().length() >= 3 && password.getText().length() < 5) {
            System.out.println("no valid password");
            pwdmessage.setText("Password has to be at least 5 characters");
            changeStyleError(password);
        } else {


            API.createUser(new ServerCallback<User, User>() {
                @Override
                public void run() {
                    if (getStatusCode() == 200) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                    try {
                        System.out.println("New user created");

                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SignUpConfirmation.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Succesful Sign Up");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                            }
                        });
                    } else {
                        System.out.println(getStatusCode());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                pwdmessage.setText("Account with this username already exists");

                            }
                        });
                    }
                }
            }, new User(username.getText(), password.getText()));
            }
        }
//    }


    public void changeStyleError(TextField field) {
        field.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 5px;");
    }


    @FXML
    public void handle(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ActionEvent ae = new ActionEvent();
            signup(ae);
        }
    }
}
