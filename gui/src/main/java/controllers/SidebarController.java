package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SidebarController {

    @FXML
    private VBox sidebar;

    @FXML
    private JFXButton overview;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton leaderboard;

    @FXML
    private JFXButton account;

    @FXML
    private JFXButton logout;

    @FXML
    private AnchorPane subscene;

    @FXML
    void accountpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/accountPage.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void account(ActionEvent event) {

    }

//    @FXML
//    void hover(ActionEvent event) {
//
//    }

    @FXML
    void leaderboardpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/leaderboard.fxml"));
        subscene.getChildren().setAll(pane);
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
    void overviewpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/trackerScreen.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void buttonhover(MouseEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        //button.toFront();
        button.setMinWidth(200);
        button.setPrefWidth(200);
    }

    @FXML
    void buttonunhover(MouseEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        button.setMinWidth(100);
        button.setPrefWidth(100);
    }
}
