package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.activity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(@Param("categoryName") String categoryName);
=======

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String categoryName);
>>>>>>> dev
}
