package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @RequestMapping("/activities")
    public List<Activity> getActivities() {
        return activityService.getAllActivities();
    }

    @RequestMapping("/activities/{id}")
    public Activity getActivity(@PathVariable UUID id) {
        return activityService.getActivity(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activities")
    public void addActivity(@RequestBody Activity activity) {
        activityService.addActivity(activity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/activities/{id}")
    public void updateActivity(@RequestBody Activity activity, @PathVariable UUID id) {
        activityService.updateActivity(id, activity);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/activities/{id}")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }
}
