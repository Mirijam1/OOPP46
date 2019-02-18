package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/activities")
    public List<Activity> getAllactivities() {
        return activityService.getAllActivities();
    }

    @RequestMapping("/activities/{id}")
    public Activity getActivity(@PathVariable String id) {
        return activityService.getActivity(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activities")
    public void addActivity(@RequestBody Activity activity) {
        activityService.addActivity(activity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/activities/{id}")
    public void updateActivity(@RequestBody Activity activity, @PathVariable String id) {
        activityService.updateActivity(id, activity);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/activities/{id}")
    public void deleteActivity(@PathVariable String id) {
        activityService.deleteActivity(id);
    }
}
