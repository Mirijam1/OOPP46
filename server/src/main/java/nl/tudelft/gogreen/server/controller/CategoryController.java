package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.Category;
import nl.tudelft.gogreen.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping("/{name}")
    public Category getCategory(@PathVariable String name) {
        return categoryService.getCategory(name);
    }
    @RequestMapping("/activity/{categoryname}")
    public Collection<Activity> getActivities(@PathVariable String categoryname) {
        return categoryService.getActivities(categoryname);
    }
}
