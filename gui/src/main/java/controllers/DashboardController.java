package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.Activity;
import nl.tudelft.gogreen.api.servermodels.Category;

import java.io.IOException;
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
    private Pane gridpane;

    @FXML
    private Label category;

    @FXML
    public void initialize() {
        //    bindProperty();
        // Start retrieval
        API.retrieveCategoryList(new ServerCallback<Object, Category[]>() {
            @Override
            public void run() {
                // Handle this a bit more gracefully, just an example
                if (getStatusCode() != 200) {
                    throw new RuntimeException("OOF");
                }

                // Replace this later by a real logger
                System.out.println("API call returned" + (isCached() ? " (cached)" : "")
                    + ", result: " + Arrays.toString(getResult()));

                List<Category> categories = Arrays.stream(getResult()).collect(Collectors.toList());

                // Call method after async call is finished
                loadCategoryList(categories);
            }
        });
    }

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
        loadActivityList();
    }

    private void loadActivityList() {
        API.retrieveActivityList(new ServerCallback<Object, Activity[]>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    throw new RuntimeException("OOF");
                }

                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());

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

    public void submitButton(ActionEvent event) throws Exception {
        if (activityBox.getSelectionModel().isEmpty() || categoryBox.getSelectionModel().isEmpty()){
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/informationPopup.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("information");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("info");
        }else{
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/confirmationPopup.fxml"));
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("confirmation");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("con");
        }
    }

    private static class CatNameStringConverter extends StringConverter<Category> {
        @Override
        public String toString(Category object) {
            return object.getCategoryName();
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
