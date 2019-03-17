package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.Badge;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

import java.util.Collection;
import java.util.UUID;

public interface IProfileService {
    Collection<Badge> findBadges(UserProfile profile);

    UserProfile createUserProfileForUser(User user);

    UserProfile getUserProfile(User user);

    SubmitResponse submitActivity(SubmittedActivity submittedActivity, User user);

    Collection<CompletedActivity> getCompletedActivities(User user, Integer limit);

    CompletedActivity getCompletedActivityDetailed(User user, UUID externalId);

    void addBadge(UserProfile user, AchievedBadge badge);

    Collection<AchievedBadge> getAchievedBadges(User user);
}
