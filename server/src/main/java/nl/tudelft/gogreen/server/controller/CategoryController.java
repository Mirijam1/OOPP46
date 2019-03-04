package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.activity.Category;
import nl.tudelft.gogreen.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping("/{id}")
    public Category getCategory(@PathVariable String name) {
        return categoryService.getCategory(name);
    }
}
