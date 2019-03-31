package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.NotFoundException;
import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.Category;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;
import nl.tudelft.gogreen.server.models.activity.config.ConfiguredOption;
import nl.tudelft.gogreen.server.models.embeddable.ConfiguredOptionId;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.activity.ActivityOptionRepository;
import nl.tudelft.gogreen.server.repository.activity.ActivityRepository;
import nl.tudelft.gogreen.server.service.handlers.ICarbonService;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.SubmittedActivityOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Component
@Service
public class ActivityService implements IActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityOptionRepository activityOptionRepository;
    private final ICategoryService categoryService;
    private final ICarbonService carbonService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           ICategoryService categoryService,
                           ActivityOptionRepository activityOptionRepository,
                           ICarbonService carbonService) {
        this.activityRepository = activityRepository;
        this.categoryService = categoryService;
        this.activityOptionRepository = activityOptionRepository;
        this.carbonService = carbonService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Activity> getActivitiesByCategory(String categoryName) {
        Category category = categoryService.getCategory(categoryName);

        return activityRepository.findByCategory(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ActivityOption> getActivityOptions(Integer activityId) {
        Activity activity = activityRepository.findActivityById(activityId);

        if (activity == null) {
            throw new NotFoundException();
        }

        return activityOptionRepository.findActivityOptionsByActivity(activity);
    }

    @Override
    public Activity getActivity(Integer id) {
        Activity activity = activityRepository.findActivityById(id);

        if (activity == null) {
            throw new NotFoundException();
        }

        return activity;
    }

    @Override
    @Transactional
    public CompletedActivity buildCompletedActivity(SubmittedActivity submittedActivity,
                                                    UserProfile user) {
        Activity activity = activityRepository.findActivityById(submittedActivity.getActivityId());
        Collection<ConfiguredOption> options = new ArrayList<>();

        if (activity == null) {
            throw new NotFoundException();
        }

        CompletedActivity completedActivity = CompletedActivity.builder()
                .id(UUID.randomUUID())
                .externalId(UUID.randomUUID())
                .activity(activity)
                .dateTimeCompleted(LocalDateTime.now())
                .profile(user)
                .achievedBadges(new ArrayList<>())
                .progressingAchievements(new ArrayList<>())
                .triggers(new ArrayList<>(activity.getTriggers()))
                .build();


        if (activity.getOptions() != null) {
            if (submittedActivity.getOptions() == null
                    || activity.getOptions().size() != submittedActivity.getOptions().size()) {
                throw new BadRequestException();
            }

            for (SubmittedActivityOption submittedActivityOption : submittedActivity.getOptions()) {
                ActivityOption activityOption = activityOptionRepository
                        .findActivityOptionById(submittedActivityOption.getOptionId());

                if (activityOption == null
                        || !activityOption.getInputType().getValidator().isValid(submittedActivityOption.getValue())) {
                    throw new BadRequestException();
                }

                ConfiguredOptionId id = ConfiguredOptionId.builder()
                        .option(activityOption)
                        .activity(completedActivity).build();

                ConfiguredOption option = ConfiguredOption.builder()
                        .id(id)
                        .inputType(activityOption.getInputType())
                        .value(submittedActivityOption.getValue()).build();

                options.add(option);
            }
        }

        // Set options
        completedActivity.setOptions(options);

        // Fetch and set points
        completedActivity.setPoints(carbonService.fetchPoints(completedActivity));

        return completedActivity;
    }
}
