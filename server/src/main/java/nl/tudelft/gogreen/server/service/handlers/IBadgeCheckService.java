package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import java.util.Collection;

public interface IBadgeCheckService {
    Collection<AchievedBadge> checkBadge(CompletedActivity completedActivity,
                                         UserProfile userProfile,
                                         Collection<Trigger> triggers);

    Collection<AchievedBadge> addBadgesAndTriggers(CompletedActivity completedActivity,
                                                   UserProfile userProfile,
                                                   Trigger trigger,
                                                   Collection<Trigger> triggers,
                                                   Collection<Trigger> workingTriggers);
}
