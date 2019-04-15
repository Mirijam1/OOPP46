package nl.tudelft.gogreen.server.tests.controllers;

import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.service.ProfileService;
import nl.tudelft.gogreen.server.service.UserDetailService;
import nl.tudelft.gogreen.shared.models.Achievement;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.SubmittedActivityOption;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class ProfileControllerCompletablesTest {
    private final String basicEndpoint = "/api/profile/";

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailService userDetailService;

    private static Integer badges;
    private static Long progressingAchievements;
    private static Long completedAchievements;

    private static boolean inserted = false;

    @BeforeTransaction
    public void setUp() {
        if (inserted) {
            return;
        }

        User user = userRepository.findUserByUsername("gogreenuser");
        userDetailService.removeUser(user);
        userDetailService.completeVerification(userDetailService.createUser("gogreenuser", "password", null));

        inserted = true;
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(0, "1"));
        options.add(new SubmittedActivityOption(7, "1"));

        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();
        SubmitResponse submitResponse = profileService.submitActivity(submittedActivity, userRepository.findUserByUsername("gogreenuser"));
        badges = submitResponse.getBadges().size();
        progressingAchievements = submitResponse.getAchievements().stream().filter(Achievement::getCompleted).count();
        completedAchievements = submitResponse.getAchievements().stream().filter(achievement -> !achievement.getCompleted()).count();
    }

    @Transactional
    @WithUserDetails(value = "gogreenuser", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnCorrectAmountOfBadges() throws Exception {
        mock.perform(get(basicEndpoint + "badges"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(badges)));
    }

    @Transactional
    @WithUserDetails(value = "gogreenuser", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnCorrectAmountOfCompletedAchievements() throws Exception {
        mock.perform(get(basicEndpoint + "achievements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(progressingAchievements.intValue())));
    }

    @Transactional
    @WithUserDetails(value = "gogreenuser", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnCorrectAmountOfProgressingAchievements() throws Exception {
        mock.perform(get(basicEndpoint + "achievements/progressing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(completedAchievements.intValue())));
    }
}
