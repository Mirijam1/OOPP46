package nl.tudelft.gogreen.server.tests.controllers.branches;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.activity.ActivityOptionRepository;
import nl.tudelft.gogreen.server.repository.activity.ActivityRepository;
import nl.tudelft.gogreen.server.service.ActivityService;
import nl.tudelft.gogreen.server.service.ICategoryService;
import nl.tudelft.gogreen.server.service.handlers.ICarbonService;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {
    private ActivityService activityService;

    @Before
    public void setUp() {
        ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
        ActivityOptionRepository activityOptionRepository = Mockito.mock(ActivityOptionRepository.class);
        ICategoryService categoryService = Mockito.mock(ICategoryService.class);
        ICarbonService carbonService = Mockito.mock(ICarbonService.class);

        Activity activity = Activity.builder().id(0).triggers(new ArrayList<>()).build();

        when(activityRepository.findActivityById(0)).thenReturn(activity);
        when(carbonService.fetchPoints(any())).thenReturn(1F);

        this.activityService = new ActivityService(activityRepository, categoryService, activityOptionRepository, carbonService);
    }

    @Test
    public void shouldReturnActivityWithoutOptions() {
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).build();
        UserProfile profile = Mockito.mock(UserProfile.class);
        CompletedActivity activity = activityService.buildCompletedActivity(submittedActivity, profile);

        assertEquals(activity.getActivity().getId(), new Integer(0));
        assertEquals(activity.getOptions().size(), 0);
    }
}
