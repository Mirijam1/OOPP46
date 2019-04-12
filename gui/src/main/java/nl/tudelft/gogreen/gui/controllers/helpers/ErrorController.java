package nl.tudelft.gogreen.gui.controllers.helpers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    private ImageView image;

    @FXML
    private Button goback;

    @FXML
    private Line line;

    @FXML
    private Label content2;

    @FXML
    private Label content1;

    @FXML
    private Label error;


    public void exit(ActionEvent event) {
        System.out.println("close");

        Stage stage = (Stage) goback.getScene().getWindow();

        stage.close();

    }
}
