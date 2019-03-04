package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.Category;
import nl.tudelft.gogreen.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Category getCategory(String name) {
        return categoryRepository.findByCategoryName(name);
    }
}
