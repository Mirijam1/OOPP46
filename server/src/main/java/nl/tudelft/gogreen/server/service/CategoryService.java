package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Category;
import nl.tudelft.gogreen.server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
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

//private List<Category> categories = new ArrayList<>(Arrays.asList(
//    new Category(5, "Spring 1", "my descrpiton"),
//    new Category(6, "Spring 2", "my descrpiton"),
//    new Category(7, "Spring Framework", "my descrpiton")
//));
//    public List<Category> getAllCategories(){
//        return categories;
//    }
    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Integer id, Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
