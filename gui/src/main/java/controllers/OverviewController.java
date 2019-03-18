package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;

public class OverviewController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label username;

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
        initUser();
    }

    private void initUser() {
        API.retrieveFakeUser(new ServerCallback<Object, User>() {

            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    user = getResult();
                    username.setText(user.getName());
                    amount.setText(user.getPoints().toString() + " Points");
                    initHistory();
                    initSuggestions();
                }
            }
        });
    }

    private void initHistory() {
        histPane.setFitToWidth(true);
        //An api method for retrieving previous activity data from the user
        String[] activityHistory = {"Added a veggie meal",
                "Took 2 or 3 less whiffs of air per day",
                "Started floating instead of walking, less movement = less CO2"};

        for (int i = 0; i < activityHistory.length; i++) {
            Label historyText = new Label(activityHistory[i]);
            Pane historyPane = new Pane();

            if (i % 2 == 1) {
                historyPane.setStyle("-fx-background-color: lightgrey;");
            } else {
                historyPane.setStyle("-fx-background-color: white;");
            }
            historyPane.setPrefSize(548, 120);
            histVBox.setPrefHeight(120 * (i + 1));

            historyPane.getChildren().add(historyText);
            histVBox.getChildren().add(historyPane);
        }
    }

    private void initSuggestions() {
        suggPane.setFitToWidth(true);
        //An api (or pseudo-server) method for retrieving suggestions to the user, for now an array of strings will do
        String[] suggestions = {"Add a veggie meal",
                "Take 2 or 3 less whiffs of air per day",
                "Start floating instead of walking, less movement = less CO2"};

        for (int i = 0; i < suggestions.length; i++) {
            Label suggestionText = new Label(suggestions[i]);
            Pane suggestion = new Pane();

            if (i % 2 == 1) {
                suggestion.setStyle("-fx-background-color: lightgrey;");
            } else {
                suggestion.setStyle("-fx-background-color: white;");
            }
            suggestion.setPrefSize(548, 120);
            suggVBox.setPrefHeight(120 * (i + 1));

            suggestion.getChildren().add(suggestionText);
            suggVBox.getChildren().add(suggestion);
        }
    }
}