package nl.tudelft.gogreen.server.tests.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.Badge;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.repository.activity.CompletedActivityRepository;
import nl.tudelft.gogreen.server.repository.completables.BadgeRepository;
import nl.tudelft.gogreen.server.service.ProfileService;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.SubmittedActivityOption;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class ProfileControllerActivitiesTest {
    private final String basicEndpoint = "/api/profile/activities";

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompletedActivityRepository completedActivityRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private ObjectMapper mapper;

    private UUID id;
    private static boolean badgeAdded = false;

    @Before
    public void setUp() {
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(0, "1"));
        options.add(new SubmittedActivityOption(7, "1"));

        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();

        for (int i = 0; i < 30; i++) {
            SubmitResponse submitResponse = profileService.submitActivity(submittedActivity, userRepository.findUserByUsername("admin"));

            if (id == null) {
                id = submitResponse.getExternalId();
            }
        }
    }

    @BeforeTransaction
    public void addCyclicBadge() {
        if (badgeAdded) {
            return;
        }

        badgeAdded = true;

        // Making two badges that trigger each other -> infinite loop -> should trigger loop guard in service
        Collection<Trigger> triggers = new ArrayList<>();
        triggers.add(Trigger.PLANTED_TREE);
        triggers.add(Trigger.PLANTED_TREE);
        nl.tudelft.gogreen.server.models.completables.Badge badge1 = Badge.builder().badgeName("b1").achievedMessage("wow1").id(424242).trigger(Trigger.COMPLETED_TRANSPORT_ACTIVITY).triggers(triggers).build();
        nl.tudelft.gogreen.server.models.completables.Badge badge2 = Badge.builder().badgeName("b2").achievedMessage("wow2").id(42424242).trigger(Trigger.PLANTED_TREE).triggers(triggers).build();

        badgeRepository.save(badge1);
        badgeRepository.save(badge2);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnCompletedActivitiesWithDefaultLimit() throws Exception {
        mock.perform(get(basicEndpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(25)));
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnCompletedActivitiesWithIgnoredLimit() throws Exception {
        mock.perform(get(basicEndpoint + "?limit=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(25)));
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnCompletedActivitiesWithLimit() throws Exception {
        mock.perform(get(basicEndpoint + "?limit=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnNotFoundWhenRequestingNonExistingActivity() throws Exception {
        mock.perform(get(basicEndpoint + "/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnOkWhenRequestingExistingActivity() throws Exception {
        mock.perform(get(basicEndpoint + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("externalId", is(id.toString())))
                .andExpect(jsonPath("activity.id", is(0)));
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldCreateAndReturnActivityWhenSubmitting() throws Exception {
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(0, "42"));
        options.add(new SubmittedActivityOption(7, "42"));
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();
        final SubmitResponse[] response = new SubmitResponse[1];

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isOk())
                .andDo(mvcResult -> response[0] = mapper.readValue(mvcResult.getResponse().getContentAsString(), SubmitResponse.class));

        CompletedActivity completedActivity = completedActivityRepository.findCompletedActivityByExternalId(response[0].getExternalId());
        final boolean[] foundTheBestNumber = new boolean[1];
        completedActivity.getOptions().forEach(option -> {
            if (option.getValue().equals("42")) {
                foundTheBestNumber[0] = true;
            }
        });

        assertTrue(foundTheBestNumber[0]);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldReturnNotFoundWhenSubmittingNonExistingActivity() throws Exception {
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(-923847).options(null).build();

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isNotFound());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldReturnBadRequestWhenSubmittingNoOptions() throws Exception {
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(null).build();

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldReturnBadRequestWhenSubmittingTooManyOptions() throws Exception {
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(0, "42"));
        options.add(new SubmittedActivityOption(0, "123"));
        options.add(new SubmittedActivityOption(0, "123"));
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldReturnBadRequestWhenSubmittingNonExistingOption() throws Exception {
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(-84762938, "42"));
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldReturnBadRequestWhenSubmittingInvalidOptionValue() throws Exception {
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(0, "defo_not_a_float"));
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    @Transactional
    public void shouldReturnInternalServerErrorOnSubmittingActivityWithCyclicBadges() throws Exception {
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(1, "1"));
        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(1).options(options).build();

        mock.perform(put(basicEndpoint + "/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(submittedActivity)))
                .andExpect(status().isInternalServerError());
    }
}
