package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.repository.query.Param;
=======
>>>>>>> dev
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
<<<<<<< HEAD
    User findByUsername(@Param("username") String username);
=======
    User findUserByUsername(String username);
>>>>>>> dev
}
