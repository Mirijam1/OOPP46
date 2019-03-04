package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(@Param("name") String name);
}
