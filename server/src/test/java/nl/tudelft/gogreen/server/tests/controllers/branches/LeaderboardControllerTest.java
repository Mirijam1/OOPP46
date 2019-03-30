package nl.tudelft.gogreen.server.tests.controllers.branches;

import nl.tudelft.gogreen.server.controller.LeaderboardController;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.Authority;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;

public class LeaderboardControllerTest {
    private LeaderboardController leaderboardController;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        this.leaderboardController = new LeaderboardController(null, null);
    }

    @Test
    public void shouldThrowUnauthorizedWhenSubmittingWithNoAuthentication() {
        exception.expect(UnauthorizedException.class);

        leaderboardController.getFriendLeaderboard(null, 0);
    }

    @Test
    public void shouldThrowUnauthorizedWhenSubmittingWithAnonymousAuthentication() {
        exception.expect(UnauthorizedException.class);

        Collection<Authority> authorities = new ArrayList<>();
        authorities.add(Mockito.mock(Authority.class));
        leaderboardController.getFriendLeaderboard(new AnonymousAuthenticationToken("s", "s", authorities), 0);
    }
}
