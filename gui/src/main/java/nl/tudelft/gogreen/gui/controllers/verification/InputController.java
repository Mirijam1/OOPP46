package nl.tudelft.gogreen.gui.controllers.verification;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InputController<T, C extends InputConverter<T>> {
    @FXML
    private TextField tokenField;
    @FXML
    private JFXTextArea messageText;
    @FXML
    private JFXTextArea dataError;
    @FXML
    private Label inputError;

    private String title;
    private String message;
    private String errorMessage;

    private Stage stage;
    private C inputConverter;
    private T result;

    /**
     * <p>Instantiates a new input screen.</p>
     * @param title A {@link String} representing the title of the screen
     * @param message A {@link String} representing the message that will be displayed
     * @param errorMessage A {@link String} representing the error message, if any, that will be displayed
     * @param inputConverter An instance of an {@link InputConverter}, which will be used to parse the input
     */
    public InputController(String title, String message, String errorMessage, C inputConverter) {
        this.title = title;
        this.message = message;
        this.errorMessage = errorMessage;
        this.inputConverter = inputConverter;
    }

    public boolean inputReceived() {
        return this.result != null;
    }

    /**
     * <p>Opens the input screen.</p>
     */
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

    public String getRawInput() {
        return tokenField.getText();
    }

    /**
     * <p>Handles imput, and makes sure ENTER result in a submit action.</p>
     * @param event The {@link KeyEvent}
     */
    @FXML
    public void handleInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            handleSubmit();
        }
    }

    /**
     * <p>Attempts to parse the input, and handles accordingly.</p>
     */
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
