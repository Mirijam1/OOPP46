package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.Category;
import nl.tudelft.gogreen.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryRepository.findAll());
    }
    public Collection<Activity> getActivities(String categoryname) {
          List<Activity> activities = categoryRepository.findActivitiesByCategoryName(categoryname);
          return activities;
    }

    public Category getCategory(String name) {
        return categoryRepository.findByCategoryName(name);
    }

}
