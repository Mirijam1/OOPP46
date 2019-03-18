package nl.tudelft.gogreen.server.repository.completables;

import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface AchievedBadgeRepository extends JpaRepository<AchievedBadge, UUID> {
    Collection<AchievedBadge> findAchievedBadgesByProfileOrderByDateTimeAchievedDesc(UserProfile profile);
}
