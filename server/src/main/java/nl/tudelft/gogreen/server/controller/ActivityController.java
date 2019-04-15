package nl.tudelft.gogreen.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    public Collection<Activity> getActivityByCategory(@PathVariable String categoryName) {
        return activityService.getActivitiesByCategory(categoryName);
    }

    @RequestMapping("/{activityId}/options")
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    public Collection<ActivityOption> getActivityOptions(@PathVariable Integer activityId) {
        return activityService.getActivityOptions(activityId);
    }

    @RequestMapping("/{activityId}")
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    public Activity getActivity(@PathVariable Integer activityId) {
        return activityService.getActivity(activityId);
    }
}
