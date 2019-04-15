package nl.tudelft.gogreen.server.repository.completables;

import nl.tudelft.gogreen.server.models.completables.Achievement;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ProgressingAchievementRepository extends JpaRepository<ProgressingAchievement, Integer> {
    Collection<ProgressingAchievement> findProgressingAchievementByProfileAndCompletedOrderByDateTimeAchievedDesc(
            UserProfile profile, Boolean completed);

    ProgressingAchievement findProgressingAchievementByProfileAndAchievement(UserProfile profile,
                                                                             Achievement achievement);

    ProgressingAchievement findProgressingAchievementById(UUID uuid);
}
