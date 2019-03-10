package nl.tudelft.gogreen.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.tudelft.gogreen.server.exceptions.NotFoundException;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.service.ProfileService;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    public @ResponseBody UserProfile getUserProfile(Authentication authentication) {
        return profileService.getUserProfile((User) authentication.getPrincipal());
    }

    @RequestMapping(path = "/activities/submit",
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody SubmitResponse submitActivity(@RequestBody SubmittedActivity submittedActivity,
                                                       Authentication authentication) {
        return profileService.submitActivity(submittedActivity, (User) authentication.getPrincipal());
    }

    @RequestMapping(path = "/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    public @ResponseBody Collection<CompletedActivity> getCompletedActivities(Authentication authentication,
                                                                              @RequestParam(value = "limit",
                                                                                  required = false)
                                                                                  Integer limit) {
        if (limit == null) {
            limit = 25;
        }

        return profileService.getCompletedActivities((User) authentication.getPrincipal(), limit);
    }

    @RequestMapping(path = "/activities/{externalId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    public @ResponseBody CompletedActivity getCompletedActivity(@PathVariable UUID externalId,
                                                                              Authentication authentication) {
        CompletedActivity completedActivity = profileService
            .getCompletedActivityDetailed((User) authentication.getPrincipal(), externalId);

        if (completedActivity == null) {
            throw new NotFoundException();
        }

        return completedActivity;
    }
}
