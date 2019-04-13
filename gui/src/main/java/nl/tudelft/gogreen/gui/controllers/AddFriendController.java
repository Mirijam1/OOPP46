package nl.tudelft.gogreen.gui.controllers;

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
import nl.tudelft.gogreen.shared.StatusCodes;
import nl.tudelft.gogreen.shared.models.UserServer;
import nl.tudelft.gogreen.shared.models.social.Friendship;
import nl.tudelft.gogreen.shared.models.social.SocialUser;

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
    private ScrollPane pendingScroll;

    @FXML
    private VBox pendingVBox;

    @FXML
    private JFXButton exit;

    @FXML
    protected void initialize() {
        API.retrievePendingReceivedFriendRequests(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initPendingFriendRequests(getResult()));
            }
        });
    }

    private void initPendingFriendRequests(Friendship[] pendingRequests) {
        if (pendingRequests.length > 0) {
            for (int i = 0; i < pendingRequests.length; i++) {
                pendingScroll.setFitToWidth(true);
                pendingVBox.setSpacing(6);
                pendingVBox.setTranslateX(3);
                pendingVBox.setTranslateY(2);

                Label userLabel = new Label(pendingRequests[i].getFriend().getUsername());
                userLabel.setTranslateX(30);
                userLabel.setTranslateY(14);

                JFXButton acceptButton = new JFXButton("Accept?");
                acceptButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: #FFFFFF;");
                acceptButton.setOnAction(acceptFriend(pendingRequests[i].getFriend(), acceptButton));
                acceptButton.setTranslateX(200);
                acceptButton.setTranslateY(9);

                Pane entryPane = new Pane();
                entryPane.setPrefSize(292, 50);
                entryPane.setMaxWidth(292);
                entryPane.setStyle("-fx-background-radius: 25; -fx-background-color: rgba(0, 0, 0, 0.50);");
                int entryHeight = 92;

                entryPane.getChildren().addAll(userLabel, acceptButton);
                pendingVBox.setPrefHeight((entryHeight + 6) * (i + 1));
                pendingVBox.getChildren().add(entryPane);
            }
        } else {
            Label noRequests = new Label("No pending requests.");
            noRequests.setTranslateX(75);
            noRequests.setStyle("-fx-text-fill: #000000;");
            pendingVBox.getChildren().add(noRequests);
        }
    }

    @FXML
    protected void exit(ActionEvent event) {
        SidebarController.controller.addfriendpage(new ActionEvent());
    }

    @FXML
    protected void searchUser(ActionEvent event) {
        if (searchField.getText().equals("")) {
            return;
        }

        String searchUserName = searchField.getText();
        API.searchForUsers(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                if (getStatusCode() == StatusCodes.OK) {
                    Platform.runLater(() -> {
                        clearSearchResults();
                        createSearchResults(getResult());
                    });
                }
            }
        }, searchUserName);
    }

    private void clearSearchResults() {
        addFriendVBox.getChildren().clear();
    }

    private void createSearchResults(UserServer[] userServer) {
        if (userServer.length > 0) {
            for (int i = 0; i < userServer.length; i++) {
                addFriendScroll.setFitToWidth(true);
                addFriendVBox.setSpacing(6);
                addFriendVBox.setTranslateX(3);
                addFriendVBox.setTranslateY(2);

                Label userLabel = new Label(userServer[i].getUser().getUsername());
                userLabel.setTranslateX(30);
                userLabel.setTranslateY(14);

                JFXButton addButton = new JFXButton("Add");
                addButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: #FFFFFF;");
                addButton.setOnAction(addFriend(userServer[i], addButton));
                addButton.setTranslateX(215);
                addButton.setTranslateY(9);

                Pane entryPane = new Pane();
                entryPane.setPrefSize(292, 50);
                entryPane.setMaxWidth(292);
                entryPane.setStyle("-fx-background-radius: 25; -fx-background-color: rgba(0, 0, 0, 0.50);");
                int entryHeight = 92;

                entryPane.getChildren().addAll(userLabel, addButton);
                addFriendVBox.setPrefHeight((entryHeight + 6) * (i + 1));
                addFriendVBox.getChildren().add(entryPane);
            }
        } else {
            Label noResults = new Label("No results found.");
            noResults.setTranslateX(92);
            noResults.setStyle("-fx-text-fill: #000000;");
            addFriendVBox.getChildren().add(noResults);
        }
    }

    private EventHandler<ActionEvent> addFriend(UserServer user, JFXButton button) {
        return event -> API.addFriend(new ServerCallback<Object, Friendship>() {

            @Override
            public void run() {
                if (getStatusCode() == 200) {
                    System.out.println(getResult());
                    Platform.runLater(() -> {
                        button.setDisable(true);
                        button.setTranslateX(170);
                        button.setText("Request Sent");
                    });
                } else if (getStatusCode() == 400) {
                    Platform.runLater(() -> {
                        button.setDisable(true);
                        button.setText("Already Friends");
                        button.setTranslateX(160);
                    });
                } else {
                    System.out.println("Errorcode: " + getStatusCode());
                }
            }
        }, user.getUser().getUsername());
    }

    private EventHandler<ActionEvent> acceptFriend(SocialUser user, JFXButton button) {
        return event -> API.addFriend(new ServerCallback<Object, Friendship>() {

            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println(getStatusCode());
                } else {
                    System.out.println(getResult());
                    Platform.runLater(() -> {
                        button.setDisable(true);
                        button.setTranslateX(190);
                        button.setText("Accepted");
                    });
                }
            }
        }, user.getUsername());
    }
}
