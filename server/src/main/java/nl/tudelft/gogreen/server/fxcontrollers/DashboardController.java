package nl.tudelft.gogreen.server.fxcontrollers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.models.Category;
import nl.tudelft.gogreen.server.service.ActivityService;
import nl.tudelft.gogreen.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static nl.tudelft.gogreen.server.callbacks.CategoryAPI.activitycall;
import static nl.tudelft.gogreen.server.callbacks.CategoryAPI.categorycall;

@Component
public class DashboardController {

    @FXML
    private ComboBox<Category> categorybox;

    @FXML
    private ComboBox<Activity> activitybox;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ActivityService activityService;

    private List<Category> categoryList;
    private List<Activity> activityList;

    @FXML
    public void initialize() throws Exception {
        categorybox.setConverter(new CategoryToStringConverter());
        categoryList = categorycall();
        activitybox.setConverter(new ActivityToStringConverter());
        activityList = activitycall();
        try {
            categorybox.setItems(FXCollections.observableArrayList(categoryList));
            activitybox.setItems(FXCollections.observableArrayList(activityList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class CategoryToStringConverter extends StringConverter<Category> {
        @Override
        public String toString(Category object) {
            return object.getCategoryName();
        }

        @Override
        public Category fromString(String string) {
            return null;
        }
    }

    private static class ActivityToStringConverter extends StringConverter<Activity> {
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
