package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return new ArrayList<>(activityRepository.findAll());
    }

    public Activity getActivity(Integer id) {
        return activityRepository.findById(id).get();

    }

    public void addActivity(Activity activity) {

        activityRepository.save(activity);
    }

    public void updateActivity(Integer id, Activity activity) {
        activityRepository.save(activity);
    }

    public void deleteActivity(Integer id) {
        activityRepository.deleteById(id);
    }

}
