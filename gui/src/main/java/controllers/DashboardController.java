package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private Label cat;

    @FXML
    public void initialize() {
        // Start retrieval
        API.retrieveCategoryList(new ServerCallback<Category[]>() {
            @Override
            public void run() {
                // Handle this a bit more gracefully, just an example
                if (getStatusCode() != 200) {
                    throw new RuntimeException("OOF");
                }

                // Replace this later by a real logger
                System.out.println("API call returned, result: " + Arrays.toString(getResult()));

                List<Category> categories = Arrays.stream(getResult()).collect(Collectors.toList());

                // Call method after async call is finished
                loadCategoryList(categories);
            }
        });
    }


    // You would make some sort of loading animation here

    private void loadCategoryList(List<Category> catlist) {
        categoryBox.setConverter(new CatNameStringConverter());
        categoryBox.setItems(FXCollections.observableArrayList(catlist));

    }

    @FXML
    void catchange(ActionEvent event) {
        cat.setText(categoryBox.getValue().getcategoryName());
        loadActivityList();
    }

    private void loadActivityList() {
        API.retrieveActivityList(new ServerCallback<Activity[]>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    throw new RuntimeException("OOF");
                }

                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());
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
