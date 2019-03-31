package nl.tudelft.gogreen.server.tests.controllers;

import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.repository.social.FriendshipConnectionRepository;
import nl.tudelft.gogreen.server.service.ProfileService;
import nl.tudelft.gogreen.server.service.SocialService;
import nl.tudelft.gogreen.server.service.UserDetailService;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.SubmittedActivityOption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class SocialControllerTest {
    private final String basicEndpoint = "/api/social";

    @Autowired
    private MockMvc mock;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FriendshipConnectionRepository friendshipConnectionRepository;

    private static boolean inserted = false;

    @BeforeTransaction
    public void setUp() {
        if (inserted) {
            return;
        }

        userDetailService.createUser("user_socialControllerTest1", "password");
        userDetailService.createUser("user_socialControllerTest2", "password");
        userDetailService.createUser("user_socialControllerTest3", "password");
        userDetailService.createUser("user_socialControllerTest4", "password");
        userDetailService.createUser("user_socialControllerTest5", "password");
        userDetailService.createUser("user_socialControllerTest6", "password");
        userDetailService.createUser("user_socialControllerTest7", "password");

        inserted = true;

        UserProfile profile1 = userRepository.findUserByUsername("user_socialControllerTest1").getProfile();
        UserProfile profile2 = userRepository.findUserByUsername("user_socialControllerTest2").getProfile();
        UserProfile profile3 = userRepository.findUserByUsername("user_socialControllerTest3").getProfile();
        UserProfile profile4 = userRepository.findUserByUsername("user_socialControllerTest4").getProfile();
        UserProfile profile5 = userRepository.findUserByUsername("user_socialControllerTest5").getProfile();
        UserProfile profile6 = userRepository.findUserByUsername("user_socialControllerTest6").getProfile();
        UserProfile profile7 = userRepository.findUserByUsername("user_socialControllerTest7").getProfile();

        // Make 1 & 2 friends
        socialService.addFriend(profile1, profile2);
        socialService.addFriend(profile2, profile1);

        // Make 1 & 6 friends
        socialService.addFriend(profile1, profile6);
        socialService.addFriend(profile6, profile1);

        // Make 6 & 7 friends
        socialService.addFriend(profile6, profile7);
        socialService.addFriend(profile7, profile6);

        // 3 sends invite to 4
        socialService.addFriend(profile3, profile4);

        // 2 sends invite to 3
        socialService.addFriend(profile2, profile3);

        // 5 sends invite to 3
        socialService.addFriend(profile5, profile3);


        // Create some (30) activities for user 7
        Collection<SubmittedActivityOption> options = new ArrayList<>();
        options.add(new SubmittedActivityOption(0, "1"));

        SubmittedActivity submittedActivity = SubmittedActivity.builder().activityId(0).options(options).build();

        for (int i = 0; i < 30; i++) {
            profileService.submitActivity(submittedActivity, userRepository.findUserByUsername("user_socialControllerTest7"));
        }
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest1", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldDeleteFriend() throws Exception {
        mock.perform(delete(basicEndpoint + "/friends/delete/user_socialControllerTest2"))
                .andExpect(status().isOk());

        UserProfile profile1 = userRepository.findUserByUsername("user_socialControllerTest1").getProfile();
        UserProfile profile2 = userRepository.findUserByUsername("user_socialControllerTest2").getProfile();
        assertNull(friendshipConnectionRepository.findFriendshipConnectionByStartUserAndInvitedUser(profile1, profile2));
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest1", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
        mock.perform(delete(basicEndpoint + "/friends/delete/1uhd1178wg27d"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest4", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnNotFoundWhenAddingNonExistentUser() throws Exception {
        mock.perform(put(basicEndpoint + "//friendsadd/auhwd7w1dh1"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest4", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnBadRequestWhenSendingRequestToYourself() throws Exception {
        mock.perform(put(basicEndpoint + "/friends/add/user_socialControllerTest4"))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest4", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnBadRequestWhenDeletingYourself() throws Exception {
        mock.perform(delete(basicEndpoint + "/friends/delete/user_socialControllerTest4"))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest1", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnBadRequestWhenDeletingNonExistingRelation() throws Exception {
        mock.perform(delete(basicEndpoint + "/friends/delete/user_socialControllerTest4"))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest5", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnBadRequestWhenAlreadyInvited() throws Exception {
        mock.perform(put(basicEndpoint + "/friends/add/user_socialControllerTest3"))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest2", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnBadRequestWhenAlreadyAccepted() throws Exception {
        mock.perform(put(basicEndpoint + "/friends/add/user_socialControllerTest1"))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest4", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldAcceptFriendRequest() throws Exception {
        mock.perform(put(basicEndpoint + "/friends/add/user_socialControllerTest3"))
                .andExpect(status().isOk());

        UserProfile profile3 = userRepository.findUserByUsername("user_socialControllerTest3").getProfile();
        UserProfile profile4 = userRepository.findUserByUsername("user_socialControllerTest4").getProfile();

        FriendshipConnection connection = friendshipConnectionRepository.findFriendshipConnectionByStartUserAndInvitedUser(profile3, profile4);

        assertNotNull(connection);
        assertTrue(connection.getAccepted());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest3", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldDenyFriendRequest() throws Exception {
        mock.perform(delete(basicEndpoint + "/friends/delete/user_socialControllerTest2"))
                .andExpect(status().isOk());

        UserProfile profile2 = userRepository.findUserByUsername("user_socialControllerTest2").getProfile();
        UserProfile profile3 = userRepository.findUserByUsername("user_socialControllerTest3").getProfile();

        assertNull(friendshipConnectionRepository.findFriendshipConnectionByStartUserAndInvitedUser(profile2, profile3));
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest6", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnFriendActivitiesExcludingSelfWithDefaultLimit() throws Exception {
        mock.perform(get(basicEndpoint + "/friends/activities?self=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(25)));
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest6", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnFriendActivitiesExcludingSelfWithIgnoredLimit() throws Exception {
        mock.perform(get(basicEndpoint + "/friends/activities?self=false&limit=0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(25)));
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest6", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnMaxTenFriendActivitiesExcludingSelf() throws Exception {
        mock.perform(get(basicEndpoint + "/friends/activities?self=false&limit=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest6", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnMaxTenFriendActivitiesIncludingSelf() throws Exception {
        mock.perform(get(basicEndpoint + "/friends/activities?limit=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Transactional
    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthorizedWhenRequestingFriendActivityAsGuest() throws Exception {
        mock.perform(get(basicEndpoint + "/friends/activities"))
                .andExpect(status().isUnauthorized());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest6", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnNotFoundWhenRequestingNonExistingUser() throws Exception {
        mock.perform(get(basicEndpoint + "/user/98daw89duahud"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @WithUserDetails(value = "user_socialControllerTest6", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnOkWhenRequestingUser() throws Exception {
        mock.perform(get(basicEndpoint + "/user/user_socialControllerTest1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("user.username", is("user_socialControllerTest1")));
    }
}
