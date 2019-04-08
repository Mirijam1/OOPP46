package nl.tudelft.gogreen.gui.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.*;

import java.util.*;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Label userId;

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
    private Button submitButton;


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
        });
    }

    @FXML
    void onCategoryChange(ActionEvent event) {
        progress.setProgress(0.25);
        category.setText(categoryBox.getValue().getCategoryName());
        loadActivityList();
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
            noInformationPopUp();
        } else {
            confirmationPopUp();
        }
    }

    @FXML
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

    private void noInformationPopUp() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText("Please don't leave the box(es) empty!");
        ImageView image = new ImageView("img/information.png");
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("OK");
        popup(image, alert);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }


    private void confirmationPopUp() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to submit this activity?");
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("CONFIRM");
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText("CANCEL");
        ImageView image = new ImageView("img/confirmation.png");
        popup(image, alert);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            submitActivity();
            congratulationsPopup();
        } else {
            alert.close();
        }

    }


    private void congratulationsPopup() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText("Congratulations");
        alert.setContentText("You added an activity successfully!");
        ImageView image = new ImageView("img/congratulation.png");
        Button okBnt = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okBnt.setText("GO BACK");
        popup(image, alert);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    private void popup(ImageView imageView, Alert alert) {
        imageView.setFitHeight(57.0);
        imageView.setFitWidth(53.0);
        alert.getDialogPane().setGraphic(imageView);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("img/leaficon.png"));

        alert.initModality(Modality.APPLICATION_MODAL);
    }
}
