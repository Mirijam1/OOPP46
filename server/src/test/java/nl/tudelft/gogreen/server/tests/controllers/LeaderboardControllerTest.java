package nl.tudelft.gogreen.server.tests.controllers;

import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.service.SocialService;
import nl.tudelft.gogreen.server.service.UserDetailService;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class LeaderboardControllerTest {
    private final String basicEndpoint = "/api/leaderboard/";

    @Autowired
    private MockMvc mock;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private SocialService socialService;

    private static boolean inserted = false;

    @BeforeTransaction
    public void setUp() {
        if (inserted) {
            return;
        }

        userDetailService.completeVerification(userDetailService.createUser("user_leaderboardControllerTest1", "password", null));
        userDetailService.completeVerification(userDetailService.createUser("user_leaderboardControllerTest2", "password", null));
        userDetailService.completeVerification(userDetailService.createUser("user_leaderboardControllerTest3", "password", null));
        inserted = true;

        UserProfile profile1 = userRepository.findUserByUsername("user_leaderboardControllerTest1").getProfile();
        UserProfile profile2 = userRepository.findUserByUsername("user_leaderboardControllerTest2").getProfile();
        UserProfile profile3 = userRepository.findUserByUsername("user_leaderboardControllerTest3").getProfile();

        // Make 1 & 2 friends
        socialService.addFriend(profile1, profile2);
        socialService.addFriend(profile2, profile1);

        // Make (pending) invites
        socialService.addFriend(profile3, profile1);
        socialService.addFriend(profile2, profile3);
    }

    @Test
    public void shouldReturnOkWhenRequestingAllPlayers() throws Exception {
        mock.perform(get(basicEndpoint + "global"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOneUserWhenRequestGlobalLeaderboardWithLimit() throws Exception {
        mock.perform(get(basicEndpoint + "global?limit=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldReturnUnauthorizedWhenRequestingFriendsAsGuest() throws Exception {
        mock.perform(get(basicEndpoint + "friends"))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnOkWhenRequestingFriendsAsUser() throws Exception {
        mock.perform(get(basicEndpoint + "friends"))
                .andExpect(status().isOk());
    }

    @Transactional
    @WithUserDetails(value = "user_leaderboardControllerTest1", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnZeroUsersWhenRequestFriendLeaderboardWithLimit() throws Exception {
        mock.perform(get(basicEndpoint + "friends?limit=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Transactional
    @WithUserDetails(value = "user_leaderboardControllerTest2", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnFriendsLeaderboardWhenRequestingFriendsLeaderboardAsStartUser() throws Exception {
        mock.perform(get(basicEndpoint + "friends"))
                .andExpect(status().isOk());
    }

    @Transactional
    @WithUserDetails(value = "user_leaderboardControllerTest1", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnFriendsLeaderboardWhenRequestingFriendsLeaderboardAsInvitedUserUser() throws Exception {
        mock.perform(get(basicEndpoint + "friends"))
                .andExpect(status().isOk());
    }
}
