package nl.tudelft.gogreen.server.tests.controllers.branches;

import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import nl.tudelft.gogreen.server.service.ProfileService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

public class ProfileServiceTest {
    private ProfileService profileService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

        when(profileRepository.findUserProfileByUserId(any())).thenReturn(null);

        this.profileService = new ProfileService(profileRepository, null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    @Test
    public void shouldThrowUserNameNotFoundExceptionWhenRequestingNonExistingUserProfile() {
        exception.expect(UsernameNotFoundException.class);

        profileService.getUserProfile(User.builder().username("test").id(null).build());
    }
}
