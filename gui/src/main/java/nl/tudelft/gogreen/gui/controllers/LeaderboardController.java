package nl.tudelft.gogreen.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.UserServer;

public class LeaderboardController {
    @FXML
    private ScrollPane friendsScroll;

    @FXML
    private VBox friendsVbox;

    @FXML
    private ScrollPane globalScroll;

    @FXML
    private VBox globalVbox;

    @FXML
    public void initialize() {
        API.retrieveFriendsLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initFriendsLeaderboard(getResult()));
            }
        });
        API.retrieveGlobalLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initGlobalLeaderboard(getResult()));
            }
        });
    }

    private void initGlobalLeaderboard(UserServer[] users) {
        globalScroll.setFitToWidth(true);
        globalVbox.setSpacing(6);
        globalVbox.setTranslateX(3);
        globalVbox.setTranslateY(2);

        for (int i = 0; i < users.length; i++) {
            if (users[i].getUser().getUsername() != null) {
                Label user = new Label(users[i].getUser().getUsername());
                user.setTranslateX(60);
                user.setTranslateY(20);

                Label points = new Label(users[i].getPoints().toString());
                points.setTranslateX(400.00);
                points.setTranslateY(20.00);

                Label rank = new Label((i+1) + ".");
                rank.setTranslateY(20);
                rank.setTranslateX(30);

                Pane userEntry = new Pane();
                userEntry.setPrefSize(492, 60);
                userEntry.setMaxWidth(492);
                userEntry.setMaxHeight(60);
                userEntry.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(0, 0, 0, 0.50);");

                userEntry.getChildren().addAll(rank, user, points);
                globalVbox.setPrefHeight(66 * (i + 1));
                globalVbox.getChildren().add(userEntry);
            }
        }
    }

    private void initFriendsLeaderboard(UserServer[] friends) {
        friendsScroll.setFitToWidth(true);
        friendsVbox.setSpacing(6);
        friendsVbox.setTranslateX(3);
        friendsVbox.setTranslateY(2);

        for (int i = 0; i < friends.length; i++) {
            if (friends[i].getUser().getUsername() != null) {
                Label user = new Label(friends[i].getUser().getUsername());
                user.setTranslateX(60);
                user.setTranslateY(20);

                Label points = new Label(friends[i].getPoints() + " Points");
                points.setTranslateX(350.00);
                points.setTranslateY(20.00);

                Label rank = new Label((i+1)+".");
                rank.setTranslateY(20);
                rank.setTranslateX(30);

                Pane friendEntry = new Pane();
                friendEntry.setPrefSize(492, 60);
                friendEntry.setMaxWidth(492);
                friendEntry.setMaxHeight(60);
                friendEntry.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(0, 0, 0, 0.50);");

                friendEntry.getChildren().addAll(rank, user, points);
                friendsVbox.setPrefHeight(66 * (i + 1));
                friendsVbox.getChildren().add(friendEntry);
            }
        }
    }
}
