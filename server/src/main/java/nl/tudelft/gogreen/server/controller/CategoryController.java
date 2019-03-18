package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.activity.Category;
import nl.tudelft.gogreen.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/")
    public Collection<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping("/{categoryName}")
    public Category getCategory(@PathVariable String categoryName) {
        return categoryService.getCategory(categoryName);
    }
}
