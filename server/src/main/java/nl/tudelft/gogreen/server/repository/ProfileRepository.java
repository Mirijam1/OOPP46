package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<UserProfile, UUID> {
<<<<<<< HEAD
    UserProfile findOneUserProfileByUserID(UUID userID);
=======
    UserProfile findUserProfileByUserID(UUID userID);
>>>>>>> dev

    UserProfile findOneUserProfileByUuid(UUID uuid);
}
