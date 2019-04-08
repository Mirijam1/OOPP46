package nl.tudelft.gogreen.gui.controllers.signup;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.gui.controllers.verification.IdConverter;
import nl.tudelft.gogreen.gui.controllers.verification.InputController;
import nl.tudelft.gogreen.gui.controllers.verification.IntegerConverter;
import nl.tudelft.gogreen.shared.StatusCodes;
import nl.tudelft.gogreen.shared.models.User;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.UUID;

public class SignUpController {

    @FXML
    private PasswordField password;

    @FXML
    private Label usermessage;

    @FXML
    private Label pwdmessage;

    @FXML
    private TextField username;

    @FXML
    private TextField emailField;

    @FXML
    private TextField emailConfirmationField;

    @FXML
    private Label emailError;

    @FXML
    void signup(ActionEvent event) {
        password.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        username.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        emailField.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        emailConfirmationField.setStyle("-fx-border-color: none ; -fx-border-width: 2px ;");
        emailError.setText("");
        usermessage.setText("");
        pwdmessage.setText("");

        if (validateUsername() & validatePassword() & validateEmail()) {
            this.submitInformation();
        }
    }

    private boolean validateUsername() {
        if (username.getText().trim().length() < 3) {
            usermessage.setText("Name too short (3 min)");
            changeStyleError(username);
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        if (password.getText().trim().length() < 5) {
            pwdmessage.setText("Password too short (5 min)");
            changeStyleError(password);
            return false;
        }

        return true;
    }

    private boolean validateEmail() {
        if (!emailField.getText().trim().equals(emailConfirmationField.getText().trim())) {
            emailError.setText("Mails do not match");
            changeStyleError(emailField);
            changeStyleError(emailConfirmationField);
            return false;
        }

        if (!EmailValidator.getInstance().isValid(emailField.getText())) {
            emailError.setText("Invalid email");
            changeStyleError(emailField);
            changeStyleError(emailConfirmationField);
            return false;
        }

        return true;
    }

    private void submitInformation() {
        API.createUser(new ServerCallback<User, User>() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getStatusCode() == StatusCodes.USER_CREATED) {
                        validateCode(getResult(), false);
                    } else {
                        usermessage.setText("Already exists");
                    }
                });
            }
        }, new User(username.getText(), password.getText(), emailField.getText()));
    }

    private void validateCode(User user, boolean error) {
        InputController<Integer, IntegerConverter> input = new InputController<>("Enter your token",
                "Please enter the code you received in the mail", error ? "Wrong code!" : null, new IntegerConverter());

        input.openScreen();
        API.submitVerificationCode(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (getStatusCode() == 200) {
                            try {
                                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SignUpConfirmation.fxml"));
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setTitle("Succesful Sign Up");
                                stage.setScene(new Scene(root));
                                stage.setResizable(false);
                                stage.show();

                                ((Stage) emailError.getScene().getWindow()).close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            validateCode(user, true);
                        }
                    }
                });
            }
        }, user, input.getResult());
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

    @FXML
    public void continueVerification() {
        InputController<UUID, IdConverter> input = new InputController<>("Enter your token",
                "Please enter the continuation token you received in the mail", null, new IdConverter());

        input.openScreen();
        validateCode(new User(null, null, null, null, input.getResult()), false);
    }
}