package nl.tudelft.gogreen.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.CompletedActivity;
import nl.tudelft.gogreen.shared.models.social.Friendship;

import java.text.DecimalFormat;


public class FriendsController {

    @FXML
    private ScrollPane friendsScroll;

    @FXML
    private VBox friendlist;

    @FXML
    private ScrollPane activityScroll;

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

    private void initFriends(Friendship[] friends) {
        friendsScroll.setFitToWidth(true);
        friendlist.setSpacing(6);

        if (friends.length > 0) {
            for (int i = 0; i < friends.length; i++) {
                if (friends[i].getFriend() != null) {
                    Label user = new Label(friends[i].getFriend().getUsername());
                    user.setTranslateY(20);
                    user.setTranslateX(60);

                    Pane FriendEntry = new Pane();
                    FriendEntry.setPrefSize(492, 60);
                    FriendEntry.setMaxWidth(492);
                    FriendEntry.setMaxHeight(60);
                    FriendEntry.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(0, 0, 0, 0.50);");
                    friendlist.setPrefHeight(66 * (i + 1));

                    Label rank = new Label((i + 1) + ".");
                    rank.setTranslateY(20);
                    rank.setTranslateX(30);
                    FriendEntry.getChildren().addAll(rank, user);

                    friendlist.getChildren().add(FriendEntry);
                }
            }
        } else {
            Label text = new Label("No friends added.");
            text.setTranslateY(20);
            text.setTranslateX(60);

            Pane noFriendsEntry = new Pane();
            noFriendsEntry.setPrefSize(492, 60);
            noFriendsEntry.setMaxWidth(492);
            noFriendsEntry.setMaxHeight(60);
            noFriendsEntry.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(0, 0, 0, 0.50);");
            noFriendsEntry.getChildren().add(text);

            friendlist.setPrefHeight(66);
            friendlist.getChildren().add(noFriendsEntry);
        }
    }

    private void initFriendsOverview(CompletedActivity[] friendActivities) {
        activityScroll.setFitToWidth(true);
        FriendOverview.setSpacing(6);

        for (int i = 0; i < friendActivities.length; i++) {
            if (friendActivities[i].getActivity() != null) {
                CompletedActivity completedActivity = friendActivities[i];
                String points = new DecimalFormat("0.0#").format(completedActivity.getPoints());
                Label activity = new Label(String.format("%2$s completed '%3$s' (%1$s points)",
                        points, completedActivity.getUsername(), completedActivity.getActivity().getActivityName()));
                activity.setTranslateY(20);
                activity.setTranslateX(60);

                Pane overviewEntry = new Pane();
                overviewEntry.setPrefSize(492, 60);
                overviewEntry.setMaxWidth(492);
                overviewEntry.setMaxHeight(60);
                overviewEntry.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(0, 0, 0, 0.50);");
                FriendOverview.setPrefHeight(66 * (i + 1));

                overviewEntry.getChildren().add(activity);
                FriendOverview.getChildren().add(overviewEntry);
            }
        }
    }
}
