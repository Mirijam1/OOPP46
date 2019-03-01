package nl.tudelft.gogreen.server.fxcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class LoginController {

    @FXML
    private Button btn;

    @FXML
    private TextField userfield;

    @FXML
    void login(ActionEvent event) throws Exception {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("GoGreen");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


