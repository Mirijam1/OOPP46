package nl.tudelft.gogreen.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.NotFoundException;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import nl.tudelft.gogreen.server.repository.UserRepository;
import nl.tudelft.gogreen.server.service.IProfileService;
import nl.tudelft.gogreen.server.service.ISocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/social")
public class SocialController {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ISocialService socialService;
    private final IProfileService profileService;

    /**
     * instantiates SocialController.
     * @param socialService socialService
     * @param userRepository userRepository
     * @param profileRepository profileRepository
     * @param profileService profileService
     */
    @Autowired
    public SocialController(ISocialService socialService,
                            UserRepository userRepository,
                            ProfileRepository profileRepository,
                            IProfileService profileService) {
        this.socialService = socialService;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }

    /**
     * method to add friends server-side.
     * @param name username of friend to add.
     * @param authentication authtoken of logged in user.
     * @return
     */
    @RequestMapping(path = "/friends/add/{name}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    public @ResponseBody
    FriendshipConnection addFriend(@PathVariable String name,
                                   Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if (name.equals(user.getUsername())) {
            throw new BadRequestException();
        }

        User target = userRepository.findUserByUsername(name);

        if (target == null) {
            throw new NotFoundException();
        }

        return socialService.addFriend(profileRepository.findUserProfileByUserId(user.getId()),
                profileRepository.findUserProfileByUserId(target.getId()));
    }

    /**
     * delete friend.
     * @param name username of friend to delete
     * @param authentication authtoken of logged in user.
     * @return
     */
    @RequestMapping(path = "/friends/delete/{name}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    public @ResponseBody
    ServerError deleteFriend(@PathVariable String name,
                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if (name.equals(user.getUsername())) {
            throw new BadRequestException();
        }

        User target = userRepository.findUserByUsername(name);

        if (target == null) {
            throw new NotFoundException();
        }

        return socialService.deleteFriend(profileRepository.findUserProfileByUserId(user.getId()),
                profileRepository.findUserProfileByUserId(target.getId()));
    }

    /**
     * get friends activities.
     *
     * @param authentication authtoken of logged in user.
     * @return
     */
    @RequestMapping(path = "/friends/activities",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    public @ResponseBody
    Collection<CompletedActivity> getFriendActivities(Authentication authentication,
                                                       @RequestParam(value = "limit",
                                                         required = false)
                                                         Integer limit,
                                                       @RequestParam(value = "self",
                                                         required = false)
                                                         Boolean self) {
        if (limit == null || limit <= 0) {
            limit = 25;
        }

        if (self == null) {
            self = true;
        }

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        return socialService.findFriendActivities(profileRepository
                .findUserProfileByUserId(((User) authentication.getPrincipal()).getId()), limit, self);
    }

    /**
     * get user from repository.
     * @param username username of user to find
     * @return
     */
    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    public @ResponseBody UserProfile getUser(@PathVariable("username") String username) {
        if (username == null) {
            throw new BadRequestException();
        }

        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new NotFoundException();
        }

        //TODO: Hidden profile

        return profileService.getUserProfile(user);
    }
}
