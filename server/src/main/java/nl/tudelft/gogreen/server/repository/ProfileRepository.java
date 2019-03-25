package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.user.UserProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<UserProfile, UUID> {
    UserProfile findUserProfileByUserId(UUID userID);

    List<UserProfile> findUserProfilesByPointsGreaterThanEqualOrderByPointsDesc(Float points, Pageable pageable);

    List<UserProfile> findUserProfilesByIdInOrIdOrderByPointsDesc(Collection<UUID> ids, UUID id, Pageable pageable);
}
