package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

import java.util.Collection;

public interface IActivityService {
    Collection<Activity> getActivitiesByCategory(String categoryName);

    Collection<ActivityOption> getActivityOptions(Integer activityId);

    Activity getActivity(Integer id);

    CompletedActivity buildCompletedActivity(SubmittedActivity submittedActivity, UserProfile user);
}
