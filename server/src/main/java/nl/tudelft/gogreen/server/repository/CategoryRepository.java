package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String>{
     List<Category> findByCategoryName(String name);

}
