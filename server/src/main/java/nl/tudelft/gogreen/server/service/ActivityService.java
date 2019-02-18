package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        List<Activity> Activities = new ArrayList<>();
        activityRepository.findAll().forEach(Activities::add);
        return Activities;
    }

    public Activity getActivity(String id) {
        Activity t = activityRepository.findById(id).get();
        return t;
    }

    public void addActivity(Activity Activity) {

        activityRepository.save(Activity);
    }

    public void updateActivity(String id, Activity Activity) {
        activityRepository.save(Activity);
    }

    public void deleteActivity(String id) {
        activityRepository.deleteById(id);
    }

}
