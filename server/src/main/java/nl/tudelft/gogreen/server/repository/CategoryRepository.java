package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(@Param("categoryname") String categoryName);

    @Query("SELECT r.activities FROM Category r where r.categoryName = :name")
    List<Activity> findActivitiesByCategoryName(@Param("name") String name);
}
