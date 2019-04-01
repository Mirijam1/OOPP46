package controllers;

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

                if (i % 2 == 1) {
                    FriendEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #EFEFEF;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                } else {
                    FriendEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #FFFFFF;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                }
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
                Label Activity = new Label(friendactivities[i].getActivity().getActivityName());
                Label user = new Label(friendactivities[i].getUsername());
                user.setTranslateX(400.00);
                user.setTranslateY(20.00);
                Activity.setTranslateY(20);

                Activity.setTranslateX(60);
                Pane OverviewEntry = new Pane();

                OverviewEntry.setPrefSize(492, 60);
                OverviewEntry.setMaxWidth(492);
                OverviewEntry.setMaxHeight(60);

                if (i % 2 == 1) {
                    OverviewEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #EFEFEF;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                } else {
                    OverviewEntry.setStyle("-fx-background-radius: 30 30 30 30; -fx-background-color: #FFFFFF;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                }
                FriendOverview.setPrefHeight(66 * (i + 1));

                Label activityID = new Label(Integer.toString(i + 1) + ".");
                activityID.setTranslateY(20);
                activityID.setTranslateX(30);
                OverviewEntry.getChildren().addAll(activityID, Activity,user);

                FriendOverview.getChildren().add(OverviewEntry);
            }
        }
    }
}
