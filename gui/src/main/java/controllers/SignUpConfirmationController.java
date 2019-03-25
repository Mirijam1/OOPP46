package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpConfirmationController {

    @FXML
    private Button signupbtn;

    @FXML
    void gotologin(ActionEvent event) {
        System.out.println("close");
        Stage currentStage = (Stage) signupbtn.getScene().getWindow();
        currentStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("GoGreen - Log In");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
