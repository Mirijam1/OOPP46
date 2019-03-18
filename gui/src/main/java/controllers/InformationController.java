package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class InformationController {

    @FXML
    private ImageView image;

    @FXML
    private Line line;

    @FXML
    private Label information;

    @FXML
    private Button ok;


    @FXML
    void exit(ActionEvent event) {
        System.out.println("exit");

        Stage stage = (Stage) ok.getScene().getWindow();

        stage.close();

    }

}
