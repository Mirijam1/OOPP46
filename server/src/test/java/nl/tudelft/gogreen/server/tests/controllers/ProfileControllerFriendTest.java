package nl.tudelft.gogreen.server.tests.controllers;

import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.AuthorityRepository;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.service.ProfileService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class ProfileControllerFriendTest {
    private final String basicEndpoint = "/api/profile/friends";

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

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

        userDetailService.removeUser(userRepository.findUserByUsername("gogreenuser"));
        userDetailService.completeVerification(userDetailService.createUser("gogreenuser", "password", null));
        userDetailService.completeVerification(userDetailService.createUser("user_profileControllerFriend", "password", null));
        userDetailService.removeUser(userRepository.findUserByUsername("admin"));
        userDetailService.completeVerification(userDetailService.createUser("admin", "password", null));
        userDetailService.completeVerification(userDetailService.createUser("user_profileControllerFriend2", "password", null));


        User adminUser = userRepository.findUserByUsername("admin");

        adminUser.getAuthorities().add(authorityRepository.findByName("ADMIN_AUTHORITY"));
        userRepository.save(adminUser);

        inserted = true;

        UserProfile adminProfile = userRepository.findUserByUsername("admin").getProfile();
        UserProfile testUserProfile = userRepository.findUserByUsername("user_profileControllerFriend").getProfile();
        UserProfile goGreenUserProfile = userRepository.findUserByUsername("gogreenuser").getProfile();
        UserProfile profile1 = userRepository.findUserByUsername("user_profileControllerFriend2").getProfile();

        socialService.addFriend(adminProfile, goGreenUserProfile);
        socialService.addFriend(adminProfile, testUserProfile);
        socialService.addFriend(testUserProfile, adminProfile);
        socialService.addFriend(profile1, adminProfile);
        socialService.addFriend(adminProfile, profile1);
    }

    @Transactional
    @WithUserDetails(value = "gogreenuser", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnOneInvite() throws Exception {
        mock.perform(get(basicEndpoint + "/invites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Transactional
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnOnePendingInvite() throws Exception {
        mock.perform(get(basicEndpoint + "/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Transactional
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnTwoFriends() throws Exception {
        mock.perform(get(basicEndpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
