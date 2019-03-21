package nl.tudelft.gogreen.server.tests.controllers.branches;

import nl.tudelft.gogreen.server.controller.UserController;
import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.ConflictException;
import nl.tudelft.gogreen.server.exceptions.ForbiddenException;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.Authority;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.service.UserDetailService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    private UserRepository repository;
    private User user;
    private Collection<Authority> authorities;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        UserDetailService detailService = Mockito.mock(UserDetailService.class);
        repository = Mockito.mock(UserRepository.class);
        user = Mockito.mock(User.class);

        userController = new UserController(detailService, repository);

        authorities = new ArrayList<>();
        authorities.add(Mockito.mock(Authority.class));
    }

    @Test
    public void shouldThrowUnauthorizedWhenNoAuth() {
        exception.expect(UnauthorizedException.class);

        userController.getDetails(null);
    }

    @Test
    public void shouldThrowUnauthorizedWhenBadAuth() {
        exception.expect(UnauthorizedException.class);

        userController.getDetails(new AnonymousAuthenticationToken("s", user, authorities));
    }

    @Test
    public void shouldThrowForbiddenWhenCreatingAsLoggedInUser() {
        exception.expect(ForbiddenException.class);

        userController.createUser(null, Mockito.mock(Authentication.class));
    }

    @Test
    public void shouldThrowUnauthorizedWhenDeletingWithNoAuth() {
        exception.expect(UnauthorizedException.class);

        userController.deleteUser(null);
    }

    @Test
    public void shouldThrowUnauthorizedWhenDeletingWithBadAuth() {
        exception.expect(UnauthorizedException.class);

        userController.deleteUser(new AnonymousAuthenticationToken("s", user, authorities));
    }

    @Test
    public void shouldThrowUnauthorizedWhenUpdatingWithNoAuth() {
        exception.expect(UnauthorizedException.class);

        userController.updateUser(null, null);
    }

    @Test
    public void shouldThrowUnauthorizedWhenUpdatingWithBadAuth() {
        exception.expect(UnauthorizedException.class);

        userController.updateUser(null, new AnonymousAuthenticationToken("s", user, authorities));
    }

    @Test
    public void shouldThrowBadRequestWhenUpdatingWithNoUser() {
        exception.expect(BadRequestException.class);

        userController.updateUser(null, new UsernamePasswordAuthenticationToken(null, null));
    }

    @Test
    public void shouldThrowBadRequestWhenUpdatingWithShortName() {
        exception.expect(BadRequestException.class);
        when(user.getUsername()).thenReturn("1");

        userController.updateUser(user, new UsernamePasswordAuthenticationToken(null, null));
    }

    @Test
    public void shouldThrowBadRequestWhenUpdatingWithShortPassword() {
        exception.expect(BadRequestException.class);
        when(user.getPassword()).thenReturn("yeet");

        userController.updateUser(user, new UsernamePasswordAuthenticationToken(null, null));
    }

    @Test
    public void shouldThrowConflictWhenUpdatingWithDuplicateName() {
        exception.expect(ConflictException.class);
        when(user.getUsername()).thenReturn("yeet");
        when(repository.findUserByUsername("yeet")).thenReturn(Mockito.mock(User.class));

        userController.updateUser(user, new UsernamePasswordAuthenticationToken(null, null));
    }
}
