package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.User;

import java.io.IOException;

public class SidebarController {

    @FXML
    private AnchorPane anchor;

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
    private Label userLabel;

    @FXML
    private AnchorPane subscene;

    private User user;

    @FXML
    public void initialize() {
        initializeUser();
        userLabel.setText(user.getName());
    }

    private void initializeUser() {
        API.retrieveFakeUser(new ServerCallback<Object, User>() {

            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("Error");
                } else {
                    user = getResult();
                }
            }
        });
    }

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
        String id = button.getId();
        switch (id) {
            case "overview":
                button.setText("Home");
                break;
            case "add":
                button.setText("Add Activity");
                break;
            case "leaderboard":
                button.setText("Leaderboard");
                break;
            case "account":
                button.setText("Account");
                break;
            case "logout":
                button.setText("Logout");
                break;
        }
    }

    @FXML
    void buttonunhover(MouseEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        button.setText("");
    }

    public void sansify(ActionEvent actionEvent) {

    }

    @FXML
    void gotoaccount(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/accountPage.fxml"));
        subscene.getChildren().setAll(pane);

    }
}
