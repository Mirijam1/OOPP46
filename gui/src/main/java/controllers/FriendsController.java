package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.Activity;
import nl.tudelft.gogreen.shared.models.CompletedActivity;
import nl.tudelft.gogreen.shared.models.social.Friendship;

import java.text.DecimalFormat;


public class FriendsController {
    @FXML
    private VBox friendlist;

    @FXML
    private ScrollPane FRlistScroll1;

    @FXML
    private VBox FriendOverview;

    @FXML
    public void initialize() {
        API.retrieveFriends(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initFriends(getResult()));
            }
        });
        API.retrieveFriendActivities(new ServerCallback<Object, CompletedActivity[]>() {
            @Override
            public void run() {
                Platform.runLater(() -> initFriendsOverview(getResult()));
            }
        });
    }


    public void initFriends(Friendship[] friends) {

        FRlistScroll1.setFitToWidth(true);
        friendlist.setSpacing(6);

        for (int i = 0; i < friends.length; i++) {
            if (friends[i].getFriend() != null) {
                Label user = new Label(friends[i].getFriend().getUsername());
                user.setTranslateY(20);
                user.setTranslateX(60);
                Pane FriendEntry = new Pane();

                FriendEntry.setPrefSize(492, 60);
                FriendEntry.setMaxWidth(492);
                FriendEntry.setMaxHeight(60);

                FriendEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: rgba(0, 0, 0, 0.62);");
                friendlist.setPrefHeight(66 * (i + 1));

                Label rank = new Label(Integer.toString(i + 1) + ".");

                rank.setTranslateY(20);
                rank.setTranslateX(30);
                FriendEntry.getChildren().addAll(rank, user);

                friendlist.getChildren().add(FriendEntry);
            }
        }
    }

    public void initFriendsOverview(CompletedActivity[] friendactivities) {

        FRlistScroll1.setFitToWidth(true);
        FriendOverview.setSpacing(6);

        for (int i = 0; i < friendactivities.length; i++) {
            if (friendactivities[i].getActivity() != null) {
                CompletedActivity completedActivity = friendactivities[i];
                String points = new DecimalFormat("0.0#").format(completedActivity.getPoints());
                Label activity = new Label(String.format("%2$s completed '%3$s' (%1$s points)",
                        points, completedActivity.getUsername(), completedActivity.getActivity().getActivityName()));
                activity.setTranslateY(20);

                activity.setTranslateX(60);
                Pane overviewEntry = new Pane();

                overviewEntry.setPrefSize(492, 60);
                overviewEntry.setMaxWidth(492);
                overviewEntry.setMaxHeight(60);

                overviewEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: rgba(0, 0, 0, 0.62);");
                FriendOverview.setPrefHeight(66 * (i + 1));

                overviewEntry.getChildren().add(activity);

                FriendOverview.getChildren().add(overviewEntry);
            }
        }
    }
}
