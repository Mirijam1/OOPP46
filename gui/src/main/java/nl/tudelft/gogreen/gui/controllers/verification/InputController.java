package nl.tudelft.gogreen.gui.controllers.verification;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InputController<T, C extends InputConverter<T>> {
    private @FXML TextField tokenField;
    private @FXML JFXTextArea messageText;
    private @FXML JFXTextArea dataError;
    private @FXML Label inputError;

    private String title;
    private String message;
    private String errorMessage;

    private Stage stage;
    private C inputConverter;
    private T result;

    public InputController(String title, String message, String errorMessage, C inputConverter) {
        this.title = title;
        this.message = message;
        this.errorMessage = errorMessage;
        this.inputConverter = inputConverter;
    }

    public boolean inputReceived() {
        return this.result != null;
    }

    public void openScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/inputScreen.fxml"));
            loader.setController(this);

            stage = new Stage();
            Parent root = loader.load();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            messageText.setText(message);

            if (errorMessage != null) {
                dataError.setText(errorMessage);
            }

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T getResult() {
        return result;
    }

    @FXML
    public void handleInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            handleSubmit();
        }
    }

    @FXML
    public void handleSubmit() {
        String token = tokenField.getText();

        try {
            result = inputConverter.convert(token);
        } catch (IllegalArgumentException e) {
            inputError.setText(inputConverter.getErrorMessage());
            return;
        }

        stage.close();
    }
}
