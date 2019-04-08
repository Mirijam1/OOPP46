package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.UserServer;
import nl.tudelft.gogreen.shared.models.social.Friendship;

public class AddFriendController {

    @FXML
    private Pane pane;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXTextField searchField;

    @FXML
    private ScrollPane addFriendScroll;

    @FXML
    private VBox addFriendVBox;

    @FXML
    private JFXButton exit;

    @FXML
    void exit(ActionEvent event) {
        SidebarController.controller.addfriendpage(new ActionEvent());
    }

    @FXML
    void searchUser(ActionEvent event) {
        if (searchField.getText().equals("")) {
            return;
        }

        String searchUserName = searchField.getText();
        System.out.println(searchUserName);

        API.searchUserProfiles(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println(getStatusCode());
                } else {
                    System.out.println(getResult());
                    Platform.runLater(() -> createEntry(getResult()));
                }
            }
        }, searchUserName);

//        searchField.setText("");
    }

    private void createEntry(UserServer userServer) {
        int entryHeight = 92;
        addFriendScroll.setFitToWidth(true);
        addFriendVBox.setSpacing(6);
        addFriendVBox.setTranslateX(3);
        addFriendVBox.setTranslateY(2);

        Label userLabel = new Label(userServer.getUser().getName());
        userLabel.setTranslateX(30);
        userLabel.setTranslateY(14);

        JFXButton addButton = new JFXButton("Add");
        addButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: #FFFFFF;");
        addButton.setOnAction(addFriend(userServer));
        addButton.setTranslateX(215);
        addButton.setTranslateY(9);

        Pane entryPane = new Pane();
        entryPane.setPrefSize(292, 50);
        entryPane.setMaxWidth(292);
        entryPane.setStyle("-fx-background-radius: 25; -fx-background-color: rgba(0, 0, 0, 0.50);");

        entryPane.getChildren().addAll(userLabel, addButton);
        addFriendVBox.setPrefHeight((entryHeight + 6));
        addFriendVBox.getChildren().add(entryPane);
    }

    private EventHandler<ActionEvent> addFriend(UserServer user) {
        return event -> API.addFriend(new ServerCallback<Object, Friendship>() {

            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println(getStatusCode());
                } else {
                    System.out.println(getResult());
                }
            }
        }, user.getUser().getUsername());
    }
}
