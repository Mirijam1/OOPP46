package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
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

    public Activity getActivity(UUID id) {
        return activityRepository.findById(id).get();

    }

    public void addActivity(Activity activity) {

        activityRepository.save(activity);
    }

    public void updateActivity(UUID id, Activity activity) {
        activityRepository.save(activity);
    }

    public void deleteActivity(UUID id) {
        activityRepository.deleteById(id);
    }

}
