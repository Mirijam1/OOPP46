package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.SubmitResponse;

import java.util.Collection;

public interface IBadgeCheckService {
    Collection<AchievedBadge> checkBadge(CompletedActivity completedActivity,
                                         UserProfile userProfile,
                                         Collection<Trigger> triggers);
}
