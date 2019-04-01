package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.UserServer;
import javafx.scene.layout.VBox;

public class LeaderboardController {


    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private ScrollPane FriendsScroll;

    @FXML
    private VBox FriendsVbox;

    @FXML
    private ScrollPane GlobalScroll;

    @FXML
    private VBox GlobalVbox;


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

    public void initGlobalLeaderboard(UserServer[] users) {

        GlobalScroll.setFitToWidth(true);
        GlobalVbox.setSpacing(6);
        GlobalVbox.setTranslateX(3);
        GlobalVbox.setTranslateY(2);

        for (int i = 0; i < users.length; i++) {
            if (users[i].getUser().getName() != null) {
                Label user = new Label(users[i].getUser().getName());
                Label points = new Label(users[i].getPoints().toString());
                points.setTranslateX(400.00);
                points.setTranslateY(20.00);
                user.setTranslateY(20);

                user.setTranslateX(60);
                Pane UserEntry = new Pane();

                UserEntry.setPrefSize(492, 60);
                UserEntry.setMaxWidth(492);
                UserEntry.setMaxHeight(60);

                if (i % 2 == 1) {
                    UserEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #EFEFEF;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                } else {
                    UserEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #FFFFFF;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                }
                GlobalVbox.setPrefHeight(66 * (i + 1));

                Label rank = new Label(Integer.toString(i+1)+".");
                rank.setTranslateY(20);
                rank.setTranslateX(30);
                UserEntry.getChildren().addAll(rank, user, points);

                GlobalVbox.getChildren().add(UserEntry);
            }
            if (i>10) break;
        }
    }


    public void initFriendsLeaderboard(UserServer[] friends) {

        FriendsScroll.setFitToWidth(true);
        FriendsVbox.setSpacing(6);
        FriendsVbox.setTranslateX(3);
        FriendsVbox.setTranslateY(2);


        for (int i = 0; i < friends.length; i++) {
            if (friends[i].getUser().getName() != null) {
                Label user = new Label(friends[i].getUser().getName());
                Label points = new Label(friends[i].getPoints().toString());
                points.setTranslateX(400.00);
                points.setTranslateY(20.00);
                user.setTranslateY(20);

                user.setTranslateX(60);
                Pane FriendEntry = new Pane();

                FriendEntry.setPrefSize(492, 60);
                FriendEntry.setMaxWidth(492);
                FriendEntry.setMaxHeight(60);

                if (i % 2 == 1) {
                    FriendEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #EFEFEF;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                } else {
                    FriendEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #FFFFFF;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                }
                FriendsVbox.setPrefHeight(66 * (i + 1));

                Label rank = new Label(Integer.toString(i+1)+".");
                rank.setTranslateY(20);
                rank.setTranslateX(30);
                FriendEntry.getChildren().addAll(rank, user, points);

                FriendsVbox.getChildren().add(FriendEntry);
            }
        }
    }



}
