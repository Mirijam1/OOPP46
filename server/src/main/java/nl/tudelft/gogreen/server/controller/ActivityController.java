package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Activity getActivity(@PathVariable Integer id) {
        return activityService.getActivity(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activities")
    public void addActivity(@RequestBody Activity activity) {
        activityService.addActivity(activity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/activities/{id}")
    public void updateActivity(@RequestBody Activity activity, @PathVariable Integer id) {
        activityService.updateActivity(id, activity);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/activities/{id}")
    public void deleteActivity(@PathVariable Integer id) {
        activityService.deleteActivity(id);
    }
}
