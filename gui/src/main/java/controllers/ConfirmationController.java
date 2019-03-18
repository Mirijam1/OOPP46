package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConfirmationController {

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private ImageView image;

    @FXML
    private Label confirmation;

    @FXML
    private Label content;


    public void confirmButton(ActionEvent event) {
        System.out.println("add activity");
    }

    public void exit(ActionEvent event) {
        System.out.println("exit");

        Stage stage = (Stage) cancel.getScene().getWindow();

        stage.close();

    }
}
