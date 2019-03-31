package controllers;

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


public class FriendsController {

    @FXML
    private AnchorPane friends;

    @FXML
    private ScrollPane FRlistScroll;

    @FXML
    private VBox FRlistVbox;


    @FXML
    public void initialize() {
        API.retrieveGlobalLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initFriendsLeaderboard(getResult()));
            }
        });
    }


    public void initFriendsLeaderboard(UserServer[] friends) {

        FRlistScroll.setFitToWidth(true);
        FRlistVbox.setSpacing(6);


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
                FRlistVbox.setPrefHeight(66 * (i + 1));

                Label rank = new Label(Integer.toString(i+1)+".");
                rank.setTranslateY(20);
                rank.setTranslateX(30);
                FriendEntry.getChildren().addAll(rank, user, points);

                FRlistVbox.getChildren().add(FriendEntry);
            }
        }
    }
}
