package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.Activity;
import nl.tudelft.gogreen.shared.models.ActivityOption;
import nl.tudelft.gogreen.shared.models.Category;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.SubmittedActivityOption;
import nl.tudelft.gogreen.shared.models.UserServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label userId;
    @FXML
    private Pane bgPaneSlider;

    @FXML
    private JFXComboBox<Category> categoryBox;

    @FXML
    private JFXComboBox<Activity> activityBox;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private ScrollPane optionPane;
    private JFXDrawer drawer;

    @FXML
    private VBox optionBox;
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
                if (getStatusCode() != 200) {
                    throw new RuntimeException("Something went wrong");
                }

                // Replace this later by a real logger
                System.out.println("API call returned" + (isCached() ? " (cached)" : "")
                        + ", result: " + Arrays.toString(getResult()));

                List<Category> categories = Arrays.stream(getResult()).collect(Collectors.toList());

                // Call method after async call is finished
                loadCategoryList(categories);
            }
        });

        optionPane.setFitToWidth(true);
        optionBox.setSpacing(6);
        optionBox.setTranslateX(3);
        optionBox.setTranslateY(2);
    }

    private void loadCategoryList(List<Category> catlist) {
        Platform.runLater(() -> {
            categoryBox.setConverter(new CatNameStringConverter());
            categoryBox.setItems(FXCollections.observableArrayList(catlist));
            hideBgPaneSlider();
        });
    }

    @FXML
    void onCategoryChange(ActionEvent event) {
        progress.setProgress(0.25);
        category.setText(categoryBox.getValue().getCategoryName());
        loadActivityList();
        hideBgPaneSlider();
    }

    private void loadActivityList() {
        API.retrieveActivityList(new ServerCallback<Object, Activity[]>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    throw new RuntimeException("Something went wrong");
                }

                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());

                if (isCached()) {
                    System.out.println("Cached response");
                }

                fillActivities(activities);
            }
        }, category.getText());
    }

    //TODO: Find a better place for this, once everything is pretty
    class OptionEntry extends Pane {
        private ActivityOption option;
        private Control control;

        OptionEntry(ActivityOption option, Control control) {
            this.option = option;
            this.control = control;
        }

        private void setValues() {
            //TODO: Get real range and default value from server
            if (control instanceof JFXSlider) {
                ((JFXSlider) control).setMin(option.getMinValue());
                ((JFXSlider) control).setMax(option.getMaxValue());
                ((JFXSlider) control).setValue(Integer.valueOf(option.getDefaultValue()));
            }
        }

        private void init() {
            Label description = new Label(option.getDescription());
            control.setTranslateX(180.00);
            control.setTranslateY(26);
            description.setTranslateY(18);
            description.setTranslateX(35);

            description.setStyle("-fx-text-fill: #009688");

            this.setPrefSize(400, 60);
            this.setMaxWidth(400);
            this.setMaxHeight(60);

            this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.62); -fx-background-radius: 20;");

            this.getChildren().addAll(description, control);
        }

        private String getOption() {
            if (control instanceof JFXSlider) {
                return ((JFXSlider) control).getValue() + "";
            } else {
                return null;
            }
        }
    }

    @FXML
    void onActivityChange(ActionEvent event) {
        progress.setProgress(0.7);

        optionBox.getChildren().clear();

        System.out.println("Loading activity " + activityBox.getValue());

        if (activityBox.getValue() != null) {
            optionBox.getChildren().add(buildSpacer());
            for (ActivityOption option : activityBox.getValue().getOptions()) {
                OptionEntry optionEntry = new OptionEntry(option, option.getInputType().getControl());

                optionEntry.setValues();
                optionEntry.init();
                optionBox.getChildren().add(optionEntry);
            }
            optionBox.getChildren().add(buildSpacer());
        }
    }

    private Pane buildSpacer() {
        Pane pane = new Pane();

        pane.setMinWidth(400);
        pane.setMaxWidth(400);
        pane.setPrefWidth(400);
        pane.setMinHeight(15);
        pane.setMaxHeight(15);
        pane.setPrefHeight(15);
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

        return pane;
    }

    @FXML
    void onDateChange(ActionEvent event) {
        progress.setProgress(0.75);
    }

    @FXML
    void onKMchange(MouseEvent event) {
        progress.setProgress(0.9);
    }

    @FXML
    void onNumberChange(MouseEvent event) {
        progress.setProgress(0.9);
    }

    private void fillActivities(List<Activity> actList) {
        Platform.runLater(() -> {
            activityBox.setConverter(new ActivityNameStringConverter());
            activityBox.setItems(FXCollections.observableArrayList(actList));
        });
    }

    public void submitButton(ActionEvent event) throws Exception {
        if (activityBox.getSelectionModel().isEmpty() || categoryBox.getSelectionModel().isEmpty()) {
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
        } else {
            submitActivity();
        }
    }

    private void submitActivity() {
        int id = activityBox.getSelectionModel().getSelectedItem().getId();
        Collection<SubmittedActivityOption> options = new ArrayList<>();

        this.populateOptions(options);

        SubmittedActivity submittedActivity = SubmittedActivity.builder()
                .activityId(id)
                .options(options)
                .build();

        API.submitActivity(new ServerCallback<SubmittedActivity, SubmitResponse>() {
            @Override
            public void run() {
                // TODO: Handle submit
                System.out.println(getResult());

                // Clear profile cache
                API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
                    @Override
                    public void run() {
                        clearCache();
                    }
                });
            }
        }, submittedActivity);
    }

    private void populateOptions(Collection<SubmittedActivityOption> options) {
        for (Node node : optionBox.getChildren()) {
            if (node instanceof OptionEntry) {
                SubmittedActivityOption option = new SubmittedActivityOption(((OptionEntry) node).option.getId(),
                        ((OptionEntry) node).getOption());

                options.add(option);
            }
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

    private void hideBgPaneSlider() {
        bgPaneSlider.setVisible(false);
    }

}
