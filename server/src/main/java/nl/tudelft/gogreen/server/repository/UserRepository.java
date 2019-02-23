package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(@Param("username") String username);
}
