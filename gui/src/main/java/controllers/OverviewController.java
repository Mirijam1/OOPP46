package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.CompletedActivity;
import nl.tudelft.gogreen.shared.models.User;
import nl.tudelft.gogreen.shared.models.UserServer;

public class OverviewController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label userWelcome;

    @FXML
    private Label amount;

    @FXML
    private ScrollPane histPane;

    @FXML
    private VBox histVBox;

    @FXML
    private ScrollPane suggPane;

    @FXML
    private VBox suggVBox;

    private User user;

    @FXML
    public void initialize() {
        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    Platform.runLater(() -> initUser(getResult()));
                }
            }
        });
        API.retrieveCompletedActivities(new ServerCallback<Object, CompletedActivity[]>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    Platform.runLater(() -> initHistory(getResult()));
                }
            }
        });
        initSuggestions();
    }

    private void initUser(UserServer userServer) {
        user = userServer.getUser();
        Float points = userServer.getPoints();
        String pointText = " Points";

        if (points <= 1f) {
            pointText = " Point";
        }

        userWelcome.setText("Welcome " + user.getUsername());
        amount.setText("Amount of CO2 Saved: " + points.toString() + pointText);
    }

    private void initHistory(CompletedActivity[] list) {
        boolean activityadded = false;
        Color textColor = Color.web("#7b7b7b");

        histPane.setFitToWidth(true);
        histVBox.setSpacing(6);
        histVBox.setTranslateX(3);
        histVBox.setTranslateY(2);

        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].getActivity() != null) {
                    activityadded = true;

                    Label activityTitle = new Label(list[i].getActivity().getActivityName());
                    activityTitle.setStyle("-fx-font-weight: bold");
                    activityTitle.setTranslateX(60);

                    Label activityDesc = new Label();
                    String description = list[i].getActivity().getDescription();


                    if (description == null) {
                        activityDesc.setText("No Description");
                    } else {
                        activityDesc.setText(description);
                    }
                    activityDesc.setTextFill(textColor);
                    activityDesc.setTranslateX(60);

                    Label activityPoints = new Label();
                    Float points = list[i].getPoints();
                    String pointText = " Points";

                    if (points <= 1f) {
                        pointText = " Point";
                    }
                    activityPoints.setText("You got " + points + pointText);
                    activityPoints.setTextFill(textColor);
                    activityPoints.setTranslateX(60);

                    VBox entryVBox = new VBox();
                    entryVBox.setPrefSize(542, 120);
                    entryVBox.setMaxWidth(542);
                    entryVBox.setPadding(new Insets(10,0,0,0));
                    entryVBox.setSpacing(3);

                    if (i % 2 == 1) {
                        entryVBox.setStyle("-fx-background-radius: 60 60 60 60; -fx-background-color: #EFEFEF;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                    } else {
                        entryVBox.setStyle("-fx-background-radius: 60 60 60 60; -fx-background-color: #FFFFFF;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
                    }
                    histVBox.setPrefHeight(126 * (i + 1));

                    entryVBox.getChildren().addAll(activityTitle,activityDesc,activityPoints);
                    histVBox.getChildren().add(entryVBox);
                }
            }
        }
        if (!activityadded) {
            histVBox.getChildren().add(new Label("No activities added"));
        }
    }

    private void initSuggestions() {
        suggPane.setFitToWidth(true);
        suggVBox.setSpacing(6);
        suggVBox.setTranslateX(3);
        suggVBox.setTranslateY(2);
        //An api (or pseudo-server) method for retrieving suggestions to the user, for now an array of strings will do
        String[] suggestions = {"Add a veggie meal",
                "Take 2 or 3 less whiffs of air per day",
                "Start floating instead of walking, less movement = less CO2",
                "Breathen't"};

        for (int i = 0; i < suggestions.length; i++) {
            Label suggestionText = new Label(suggestions[i]);
            suggestionText.setTranslateX(60);
            Pane suggestion = new Pane();

            suggestion.setPrefSize(542, 120);
            suggestion.setMaxWidth(542);
            suggestion.setMaxHeight(120);

            if (i % 2 == 1) {
                suggestion.setStyle("-fx-background-radius: 60 60 60 60; -fx-background-color: #EFEFEF;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
            } else {
                suggestion.setStyle("-fx-background-radius: 60 60 60 60; -fx-background-color: #FFFFFF;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 2, 0, 0, 0);");
            }
            suggVBox.setPrefHeight(126 * (i + 1));

            suggestion.getChildren().add(suggestionText);
            suggVBox.getChildren().add(suggestion);
        }
    }
}