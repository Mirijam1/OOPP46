package nl.tudelft.gogreen.server.tests.controllers.branches;

import nl.tudelft.gogreen.server.controller.SocialController;
import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.NotFoundException;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.Authority;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

public class SocialControllerTest {
    private SocialController socialController;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        when(userRepository.findUserByUsername("test")).thenReturn(null);

        this.socialController = new SocialController(null,
                userRepository,
                null,
                null);
    }

    @Test
    public void shouldThrowUnauthorizedWhenRequestingFriendActivityWithNoAuth() {
        exception.expect(UnauthorizedException.class);

        socialController.getFriendActivities(null, 0, false);
    }

    @Test
    public void shouldThrowUnauthorizedWhenRequestingFriendActivityWithBadAuth() {
        exception.expect(UnauthorizedException.class);

        Collection<Authority> authorities = new ArrayList<>();
        authorities.add(Mockito.mock(Authority.class));
        socialController.getFriendActivities(new AnonymousAuthenticationToken("s", "s", authorities), 0, false);
    }

    @Test
    public void shouldThrowBadRequestWhenRequestingNullProfile() {
        exception.expect(BadRequestException.class);

        socialController.getUser(null);
    }

    @Test
    public void shouldThrowNotFoundWhenAddingUserWithNoProfile() {
        exception.expect(NotFoundException.class);

        Authentication authentication = Mockito.mock(Authentication.class);
        User user = Mockito.mock(User.class);

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn("not test");

        socialController.addFriend("test", authentication);
    }
}
