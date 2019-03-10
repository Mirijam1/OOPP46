package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
<<<<<<< HEAD
=======
import javafx.application.Platform;
>>>>>>> dev
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
<<<<<<< HEAD
=======
import javafx.scene.layout.Pane;
>>>>>>> dev
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.Activity;
import nl.tudelft.gogreen.api.servermodels.Category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private Label userId;

    @FXML
    private JFXComboBox<Category> categoryBox;

    @FXML
    private JFXComboBox<Activity> activityBox;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private VBox sidebarbox;
<<<<<<< HEAD

    @FXML
    private Label cat;

    @FXML
    public void initialize() {
        // Start retrieval
        API.retrieveCategoryList(new ServerCallback<Category[]>() {
=======
    @FXML
    private Pane gridpane;

    @FXML
    private Label category;

    @FXML
    public void initialize() {
        //    bindProperty();
        // Start retrieval
        API.retrieveCategoryList(new ServerCallback<Object, Category[]>() {
>>>>>>> dev
            @Override
            public void run() {
                // Handle this a bit more gracefully, just an example
                if (getStatusCode() != 200) {
                    throw new RuntimeException("OOF");
                }

                // Replace this later by a real logger
<<<<<<< HEAD
                System.out.println("API call returned, result: " + Arrays.toString(getResult()));
=======
                System.out.println("API call returned" + (isCached() ? " (cached)" : "")
                    + ", result: " + Arrays.toString(getResult()));
>>>>>>> dev

                List<Category> categories = Arrays.stream(getResult()).collect(Collectors.toList());

                // Call method after async call is finished
                loadCategoryList(categories);
            }
        });
    }

<<<<<<< HEAD

    // You would make some sort of loading animation here

    private void loadCategoryList(List<Category> catlist) {
        categoryBox.setConverter(new CatNameStringConverter());
        categoryBox.setItems(FXCollections.observableArrayList(catlist));

    }

    @FXML
    void catchange(ActionEvent event) {
        cat.setText(categoryBox.getValue().getcategoryName());
=======
    private void bindProperty() {
        sidebarbox.prefWidthProperty().bind(gridpane.widthProperty());
        sidebarbox.prefHeightProperty().bind(gridpane.heightProperty());
    }


    private void loadCategoryList(List<Category> catlist) {
        Platform.runLater(() -> {
            categoryBox.setConverter(new CatNameStringConverter());
            categoryBox.setItems(FXCollections.observableArrayList(catlist));
        });
    }

    @FXML
    void onCategoryChange(ActionEvent event) {
        category.setText(categoryBox.getValue().getCategoryName());
>>>>>>> dev
        loadActivityList();
    }

    private void loadActivityList() {
<<<<<<< HEAD
        API.retrieveActivityList(new ServerCallback<Activity[]>() {
=======
        API.retrieveActivityList(new ServerCallback<Object, Activity[]>() {
>>>>>>> dev
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    throw new RuntimeException("OOF");
                }

                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());
<<<<<<< HEAD
                // Call method after async call is finished

                fillActivities(activities, cat.getText());
            }
        });

    }

    private void fillActivities(List<Activity> actList, String category) {
        //List<Activity> filterList = actList.stream().filter(activity -> activity.getcatName().contains(category)).collect(Collectors.toList());

        activityBox.setConverter(new ActivityNameStringConverter());
        activityBox.setItems(FXCollections.observableArrayList(actList));
    }


    private static class CatNameStringConverter extends StringConverter<Category> {
        @Override
        public String toString(Category object) {
            return object.getcategoryName();
=======

                if (isCached()) {
                    System.out.println("Cached response");
                }

                fillActivities(activities);
            }
        }, category.getText());

    }

    private void fillActivities(List<Activity> actList) {
        Platform.runLater(() -> {
            activityBox.setConverter(new ActivityNameStringConverter());
            activityBox.setItems(FXCollections.observableArrayList(actList));
        });
    }

    private static class CatNameStringConverter extends StringConverter<Category> {
        @Override
        public String toString(Category object) {
            return object.getCategoryName();
>>>>>>> dev
        }

        @Override
        public Category fromString(String string) {
            return null;
        }
    }

    private class ActivityNameStringConverter extends StringConverter<Activity> {
        @Override
        public String toString(Activity object) {
            return object.getActivityName();
        }

        @Override
        public Activity fromString(String string) {
            return null;
        }
    }

}
