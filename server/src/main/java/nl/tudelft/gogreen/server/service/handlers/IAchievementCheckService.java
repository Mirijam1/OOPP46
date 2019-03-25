package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface IAchievementCheckService {
    Set<UUID> checkAchievements(CompletedActivity completedActivity,
                                UserProfile userProfile,
                                Collection<Trigger> triggers);
}
