package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SidebarController {

    @FXML
    private VBox sidebar;

    @FXML
    private Button overview;

    @FXML
    private JFXButton add;

    @FXML
    private Button leaderboard;

    @FXML
    private Button account;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane subscene;

    @FXML
    void accountpage(ActionEvent event) {

    }
    @FXML
    void account(ActionEvent event) throws IOException  {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/accountPage.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void hover(MouseEvent event) {

    }

    @FXML
    void leaderboardpage(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void newactivitypage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
        subscene.getChildren().setAll(pane);

    }

    @FXML
    void overviewpage(ActionEvent event) {

    }
}
