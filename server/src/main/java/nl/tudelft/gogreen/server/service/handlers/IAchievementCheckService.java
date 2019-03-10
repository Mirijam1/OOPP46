package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.SubmitResponse;

public interface IAchievementCheckService {
    void checkAchievements(CompletedActivity completedActivity,
                           UserProfile userProfile,
                           SubmitResponse submitResponse);
}
