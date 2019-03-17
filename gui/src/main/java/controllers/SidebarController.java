package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

>>>>>>> dev
public class SidebarController {

    @FXML
    private VBox sidebar;

    @FXML
    private Button overview;

    @FXML
    private Button add;

    @FXML
    private Button leaderboard;

    @FXML
    private Button account;

    @FXML
    private Button logout;

    @FXML
    private VBox subscene;

    @FXML
<<<<<<< HEAD
=======
    void accountpage(ActionEvent event) {

    }
    @FXML
    void account(ActionEvent event) throws IOException  {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/accountPage.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
>>>>>>> dev
    void hover(MouseEvent event) {

    }

<<<<<<< HEAD
=======
    @FXML
    void leaderboardpage(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void newactivitypage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
        subscene.getChildren().setAll(pane);

    }

    @FXML
    void overviewpage(ActionEvent event) {

    }
>>>>>>> dev
}
