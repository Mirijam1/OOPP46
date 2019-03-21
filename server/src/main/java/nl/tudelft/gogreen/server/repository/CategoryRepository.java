package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.activity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String categoryName);
}
