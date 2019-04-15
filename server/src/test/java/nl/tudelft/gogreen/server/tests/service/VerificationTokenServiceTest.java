package nl.tudelft.gogreen.server.tests.service;

import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.VerificationToken;
import nl.tudelft.gogreen.server.repository.VerificationTokenRepository;
import nl.tudelft.gogreen.server.service.VerificationTokenService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class VerificationTokenServiceTest {
    private VerificationTokenService service;
    private VerificationTokenRepository repository;

    @Before
    public void setUp() {
        repository = Mockito.mock(VerificationTokenRepository.class);
        service = new VerificationTokenService(repository);
    }

    @Test
    public void shouldReturnNotFoundWhenNoTokenFound() {
        when(repository.findByTokenAndUserExternalId(eq(42), any())).thenReturn(null);

        assertEquals(HttpStatus.NOT_FOUND, service.verifyToken(42, Mockito.mock(User.class)));
    }

    @Test
    public void shouldReturnNotFoundWhenIncorrectId() {
        VerificationToken token = Mockito.mock(VerificationToken.class);
        User user = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        when(repository.findByTokenAndUserExternalId(eq(4242), any())).thenReturn(token);
        when(token.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(UUID.randomUUID());
        when(user2.getId()).thenReturn(UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND, service.verifyToken(4242, user2));
    }

    @Test
    public void shouldReturnForbiddenWhenExpired() {
        VerificationToken token = Mockito.mock(VerificationToken.class);
        User user = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        UUID id = UUID.randomUUID();

        when(repository.findByTokenAndUserExternalId(eq(4242), any())).thenReturn(token);
        when(token.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(id);
        when(user2.getId()).thenReturn(id);
        when(token.getExpiresAt()).thenReturn(1L);

        assertEquals(HttpStatus.FORBIDDEN, service.verifyToken(4242, user2));
    }

    @Test
    public void shouldReturnOkWhenOk() {
        VerificationToken token = Mockito.mock(VerificationToken.class);
        User user = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        UUID id = UUID.randomUUID();

        when(repository.findByTokenAndUserExternalId(eq(4242), any())).thenReturn(token);
        when(token.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(id);
        when(user2.getId()).thenReturn(id);
        when(token.getExpiresAt()).thenReturn(Long.MAX_VALUE);

        assertEquals(HttpStatus.OK, service.verifyToken(4242, user2));
    }
}
