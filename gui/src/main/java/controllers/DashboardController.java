package controllers;

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



}
