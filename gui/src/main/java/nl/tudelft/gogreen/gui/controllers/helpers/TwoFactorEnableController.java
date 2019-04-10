package nl.tudelft.gogreen.gui.controllers.helpers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.gui.controllers.verification.IntegerConverter;
import nl.tudelft.gogreen.shared.StatusCodes;

import java.io.IOException;

public class TwoFactorEnableController {
    @FXML
    private Label inputError;
    @FXML
    private TextField tokenField;

    @FXML
    private VBox qrBox;

    private Stage stage;
    private String qrUrl;
    private IntegerConverter converter;
    private boolean enabled;

    public TwoFactorEnableController(String qrUrl, IntegerConverter converter) {
        this.qrUrl = qrUrl;
        this.converter = converter;
        this.enabled = false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void openScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/2faEnable.fxml"));
            loader.setController(this);

            stage = new Stage();
            Parent root = loader.load();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Enable 2FA");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            loadQR();

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQR() {
        try {
            ImageView imageView = new ImageView(new Image(this.qrUrl));

            this.qrBox.getChildren().add(imageView);
        } catch (IllegalArgumentException exception) {
            System.out.println("Could not load image");
        }
    }

    private void submitToken(String token) {
        API.confirmTwoFactorAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (getStatusCode() == StatusCodes.UNAUTHORIZED) {
                        inputError.setText("Invalid token");
                    } else {
                        enabled = true;
                        stage.close();
                    }
                });
            }
        }, token);
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
            converter.convert(token);
        } catch (IllegalArgumentException e) {
            inputError.setText(converter.getErrorMessage());
            return;
        }

        submitToken(token);
    }
}
