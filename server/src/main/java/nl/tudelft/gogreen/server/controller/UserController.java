package nl.tudelft.gogreen.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.ConflictException;
import nl.tudelft.gogreen.server.exceptions.ForbiddenException;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserDetailService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserDetailService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    @RequestMapping(
        path = "/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getDetails(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        return (User) authentication.getPrincipal();
    }

    @RequestMapping(
        path = "/create",
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User createUser(@RequestBody User user, Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            throw new ForbiddenException();
        }

        // TODO: Add some password+name checks

        if (user.getUsername() == null
            || user.getPassword() == null
            || user.getUsername().trim().length() < 3
            || user.getPassword().trim().length() < 5) {
            throw new BadRequestException();
        }

        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new ConflictException();
        }

        return userService.createUser(user.getUsername(), user.getPassword());
    }

    @RequestMapping(
        path = "/delete",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<String, String> deleteUser(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        userService.removeUser((User) authentication.getPrincipal());

        // TODO: Find something better than static call
        SecurityContextHolder.clearContext();

        return Collections.singletonMap("response", "DELETED");
    }

    @RequestMapping(
        path = "/update",
        method = RequestMethod.PATCH,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User updateUser(@RequestBody User user, Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        //TODO: Add password checks

        if ((user.getUsername() != null && user.getUsername().trim().length() < 3)
            || (user.getPassword() != null && user.getPassword().trim().length() < 5)) {
            throw new BadRequestException();
        }

        if (user.getUsername() != null && userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new ConflictException();
        }

        User loadedUser = (User) authentication.getPrincipal();

        return userService.updateUser(user, loadedUser);
    }
}
