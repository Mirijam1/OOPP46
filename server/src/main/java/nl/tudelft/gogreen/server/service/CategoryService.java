package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Category;
import nl.tudelft.gogreen.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Category getCategory(String id) {
        Category t = categoryRepository.findById(id).get();
        return t;
    }

    public void addCategory(Category Category) {
        categoryRepository.save(Category);
    }

    public void updateCategory(String id, Category Category) {
        categoryRepository.save(Category);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
