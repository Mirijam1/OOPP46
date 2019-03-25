package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

import java.util.Collection;
import java.util.UUID;

public interface IProfileService {
    UserProfile createUserProfileForUser(User user);

    UserProfile getUserProfile(User user);

    SubmitResponse submitActivity(SubmittedActivity submittedActivity, User user);

    Collection<CompletedActivity> getCompletedActivities(User user, Integer limit);

    CompletedActivity getCompletedActivityDetailed(User user, UUID externalId);

    void addBadge(UserProfile user, AchievedBadge badge);

    void addAchievement(UserProfile user, ProgressingAchievement achievement);

    Collection<AchievedBadge> getAchievedBadges(User user);

    Collection<ProgressingAchievement> getAchievedAchievements(User user);

    Collection<ProgressingAchievement> getProgressingAchievements(User user);
}
