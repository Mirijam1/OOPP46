package controllers;


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
import javafx.stage.Stage;

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
        Parent root;
        password.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        username.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        usermessage.setText("");
        pwdmessage.setText("");

        if (username.getText().length() < 3 && password.getText().length() < 3) {
            System.out.println("no username and password");
            usermessage.setText("Username has to be at least 3 characters");
            pwdmessage.setText("Password has to be at least 3 characters.");
            changeStyleError(password);
            changeStyleError(username);
        } else if (username.getText().length() < 3 && password.getText().length() >= 3) {
            System.out.println("no valid username");
            usermessage.setText("Username has to be at least 3 characters");
            changeStyleError(username);
        } else if (username.getText().length() >= 3 && password.getText().length() < 3) {
            System.out.println("no valid password");
            pwdmessage.setText("Password has to be at least 3 characters");
            changeStyleError(password);
        } else {
            System.out.println("New user created");

            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));

                Stage stage = new Stage();
                stage.setTitle("GoGreen");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();

                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
