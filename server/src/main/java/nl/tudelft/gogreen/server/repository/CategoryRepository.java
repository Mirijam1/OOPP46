package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Category findByCategoryname(@Param("categoryname") String CategoryName);

}
