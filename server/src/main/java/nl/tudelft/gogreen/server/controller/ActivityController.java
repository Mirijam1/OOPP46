package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/")
    public List<Activity> getActivities() {
        return activityService.getAllActivities();
    }

    @RequestMapping("/{id}")
    public Activity getActivity(@PathVariable Integer id) {
        return activityService.getActivity(id);
    }
}
