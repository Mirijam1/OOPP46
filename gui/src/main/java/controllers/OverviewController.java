package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.CompletedActivity;
import nl.tudelft.gogreen.shared.models.User;
import nl.tudelft.gogreen.shared.models.UserServer;

import java.io.IOException;

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

    private Float points;

    @FXML
    public void initialize() {
        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    Platform.runLater(() -> {
                        initUser(getResult());
                        initSuggestions();
                    });
                }
            }
        });
        API.retrieveCompletedActivities(new ServerCallback<Object, CompletedActivity[]>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    Platform.runLater(() -> initActivityHistory(getResult()));
                }
            }
        });
    }

    private void initUser(UserServer userServer) {
        User user = userServer.getUser();
        points = userServer.getPoints();
        String pointText = " Points";

        if (points <= 1f) {
            pointText = " Point";
        }

        userWelcome.setText("Welcome " + user.getUsername());
        amount.setTextFill(Color.WHITE);
        amount.setText("Amount of CO2 Saved: " + points.toString() + pointText);
    }

    private void initActivityHistory(CompletedActivity[] list) {
        int entryHeight = 92;
        histPane.setFitToWidth(true);
        histVBox.setSpacing(6);
        histVBox.setTranslateX(3);
        histVBox.setTranslateY(2);

        if (list.length > 0) {
            for (int i = 0; i < list.length; i++) {
                Label activityTitle = new Label(list[i].getActivity().getActivityName());
                activityTitle.setStyle("-fx-font-weight: bold");
                activityTitle.setTranslateX(45);

                Label activityDesc = new Label();
                String description = list[i].getActivity().getDescription();
                if (description == null) {
                    activityDesc.setText("No description");
                } else {
                    activityDesc.setText(description);
                }
                activityDesc.setTranslateX(45);

                Label activityPoints = new Label();
                Float points = list[i].getPoints();
                String pointText = " Points";
                if (points <= 1f) {
                    pointText = " Point";
                }
                activityPoints.setText("You got " + points + pointText);
                activityPoints.setTranslateX(45);

                VBox entryVBox = createEntry(entryHeight);
                entryVBox.getChildren().addAll(activityTitle, activityDesc, activityPoints);
                histVBox.setPrefHeight((entryHeight + 6) * (i + 1));
                histVBox.getChildren().add(entryVBox);
            }
        } else {
            Label messageText = new Label("No Activity Submitted!");
            messageText.setStyle("-fx-font-weight: bold");
            messageText.setTranslateX(60);

            JFXButton activityButton = new JFXButton();
            activityButton.setText("Add your first activity!");
            activityButton.setOnAction(toAddActivityPage());
            activityButton.setStyle("-fx-background-color: #EFF0EB;");
            activityButton.setRipplerFill(Color.LAWNGREEN);
            activityButton.setTranslateX(58);
            activityButton.setTranslateY(8);

            VBox zeroEntriesMessage = createEntry(entryHeight);
            zeroEntriesMessage.getChildren().addAll(messageText, activityButton);
            histVBox.setPrefHeight(entryHeight + 6);
            histVBox.getChildren().add(zeroEntriesMessage);
        }
    }

    private void initSuggestions() {
        int entryHeight = 88;
        suggPane.setFitToWidth(true);
        suggVBox.setPrefHeight(376);
        suggVBox.setSpacing(6);
        suggVBox.setTranslateX(3);
        suggVBox.setTranslateY(2);

        String[] suggestions = {"Eat a vegetarian meal and add it to your activities!",
                "Buy local produce and add it to your activities!",
                "Take the bike instead of the car for 10km or take the bus",
                "Replace 2 traditional lights with LED lights", "Go vegetarian for an entire week!",
                "Travel by train for at least 25km", "Lower the temperature in your house by 3 degrees!",
                "Plant a tree in your garden!", "Install at least 2 solar panels!",
                "Buy local produce for a whole week!", "Travel by bike for at least 25km",
                "Replace all the lights in your house with LED lights!", "Go vegan for life!",
                "Get all your energy from your solar panels", "Plant a tree every week!",
                "Only use the bike or public transport from now on"};

        int i = calculateI();
        int j = i + 4;

        while (i < j) {
            Label suggestionText = new Label(suggestions[i]);
            suggestionText.setStyle("-fx-font-weight: bold");
            suggestionText.setTranslateX(44);
            suggestionText.setTranslateY(21);

            VBox suggestion = createEntry(entryHeight);
            suggestion.getChildren().add(suggestionText);
            suggVBox.getChildren().add(suggestion);
            i++;
        }
    }

    private int calculateI() {
        if (points < 500) {
            return 0;
        }
        if (points >= 500 && points < 1000) {
            return 4;
        }
        if (points >= 1000 && points < 5000) {
            return 8;
        }
        if (points > 5000) {
            return 12;
        }
        return 0;
    }

    private VBox createEntry(int height) {
        VBox entry = new VBox();
        entry.setPrefSize(542, height);
        entry.setMaxWidth(542);
        entry.setPadding(new Insets(10, 0, 0, 0));
        entry.setSpacing(3);

        StringBuilder style = new StringBuilder();
        style.append("-fx-background-radius:");
        for (int i = 0; i < 4; i++) {
            style.append(" ");
            style.append(height / 2);
        }
        style.append("; -fx-background-color: rgba(0, 0, 0, 0.50);");
        entry.setStyle(style.toString());

        return entry;
    }

    private EventHandler<ActionEvent> toAddActivityPage() {
        return event -> {
            try {
                SidebarController.controller.newactivitypage(new ActionEvent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}