package nl.tudelft.gogreen.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.ConflictException;
import nl.tudelft.gogreen.server.exceptions.ForbiddenException;
import nl.tudelft.gogreen.server.exceptions.NotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserDetailService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserDetailService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * get user details.
     * @param authentication authtoken for logged in user.
     * @return User
     */
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    @RequestMapping(
            path = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
        User getDetails(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        return (User) authentication.getPrincipal();
    }

    /**
     * create user with params.
     * @param user user details
     * @param authentication authtoken
     * @return User
     */
    @RequestMapping(
            path = "/create",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
        User createUser(@RequestBody User user, Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            throw new ForbiddenException();
        }

        if (user == null
                || user.getUsername() == null
                || user.getPassword() == null
                || user.getUsername().trim().length() < 3
                || user.getPassword().trim().length() < 5
                || user.getMail() == null
                || !user.getMail().contains("@")) {
            throw new BadRequestException();
        }

        User foundByUsername = userRepository.findUserByUsername(user.getUsername());
        if (foundByUsername != null
                || userRepository.findUserByMail(user.getMail()) != null) {
            throw new ConflictException(foundByUsername == null ? "mail" : "name");
        }

        User createdUser = userService.createUser(user.getUsername(), user.getPassword(), user.getMail());

        userService.generateToken(createdUser);

        return createdUser;
    }

    /**
     * delete user.
     * @param authentication authtoken
     * @return Map
     */
    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
        Map<String, String> deleteUser(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        userService.removeUser((User) authentication.getPrincipal());

        // TODO: Find something better than static call
        SecurityContextHolder.clearContext();

        return Collections.singletonMap("response", "DELETED");
    }

    /**
     * update user.
     * @param user user details.
     * @param authentication authtoken.
     * @return User
     */
    @RequestMapping(
            path = "/update",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
        User updateUser(@RequestBody User user, Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        //TODO: Add password checks

        if (user == null
                || (user.getUsername() != null && user.getUsername().trim().length() < 3)
                || (user.getPassword() != null && user.getPassword().trim().length() < 5)) {
            throw new BadRequestException();
        }

        if (user.getUsername() != null && userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new ConflictException();
        }

        User loadedUser = (User) authentication.getPrincipal();

        return userService.updateUser(user, loadedUser);
    }

    /**
     * verify user by authtoken.
     * @param externalId externalID of user.
     * @param token authtoken
     * @return BasicResponse
     */
    @RequestMapping(path = "/verify/{externalId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
        BasicResponse verifyUser(@PathVariable("externalId") UUID externalId, @RequestParam("token") Integer token) {
        User user = userRepository.findUserByExternalId(externalId);

        if (user == null) {
            throw new NotFoundException();
        }

        return userService.verifyUser(user, token);
    }

    @RequestMapping(path = "/2fa/toggle/{toggle}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody BasicResponse toggle2FA(Authentication authentication,
                                                 @PathVariable("toggle") Boolean enabled)
            throws UnsupportedEncodingException {
        User user = (User) authentication.getPrincipal();

        if (enabled) {
            if (user.isTfaEnabled()) {
                throw new BadRequestException();
            }

            return new BasicResponse(HttpStatus.OK.getReasonPhrase(),
                    userService.generateQRUrlFor2FA(user));
        } else {
            userService.disable2FA(user);
        }

        return new BasicResponse(HttpStatus.OK.getReasonPhrase(), null);
    }

    @RequestMapping(path = "/2fa/enable/{token}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody BasicResponse verify2FA(Authentication authentication,
                                                 @PathVariable("token") Long token) {
        User user = (User) authentication.getPrincipal();

        if (user.isTfaEnabled()) {
            throw new BadRequestException();
        }

        userService.enable2FA(user, token.toString());
        return new BasicResponse(HttpStatus.OK.getReasonPhrase(), null);
    }
}
