package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;
import nl.tudelft.gogreen.server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/activities/")
public class ActivityController {
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RequestMapping("/category/{categoryName}")
    public Collection<Activity> getActivityByCategory(@PathVariable String categoryName) {
        return activityService.getActivitiesByCategory(categoryName);
    }

    @RequestMapping("/{activityId}/options")
    public Collection<ActivityOption> getActivityOptions(@PathVariable Integer activityId) {
        return activityService.getActivityOptions(activityId);
    }

    @RequestMapping("/{activityId}")
    public Activity getActivity(@PathVariable Integer activityId) {
        return activityService.getActivity(activityId);
    }
}
