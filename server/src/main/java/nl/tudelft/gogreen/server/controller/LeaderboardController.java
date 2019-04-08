package nl.tudelft.gogreen.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import nl.tudelft.gogreen.server.service.ILeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final ILeaderboardService leaderboardService;
    private final ProfileRepository profileRepository;

    @Autowired
    public LeaderboardController(ILeaderboardService leaderboardService,
                                 ProfileRepository profileRepository) {
        this.leaderboardService = leaderboardService;
        this.profileRepository = profileRepository;
    }

    /**
     * retrieve global leaderboard.
     * @param limit no of entries
     * @return Collection of UserProfile
     */

    @RequestMapping(path = "/global",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    public @ResponseBody
        Collection<UserProfile> getGlobalLeaderboard(@RequestParam(value = "limit",
            required = false)
                                                         Integer limit) {
        if (limit == null) {
            limit = 25;
        }

        return leaderboardService.getGlobalLeaderBoard(limit);
    }

    /**
     *  get user's friends leaderboard.
     * @param authentication token of logged in user
     * @param limit no of entries
     * @return Collection of UserProfile
     */

    @RequestMapping(path = "/friends",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
    public @ResponseBody
        Collection<UserProfile> getFriendLeaderboard(Authentication authentication,
                                                 @RequestParam(value = "limit",
                                                         required = false)
                                                         Integer limit) {
        if (limit == null || limit <= 0) {
            limit = 25;
        }

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        UserProfile profile = profileRepository.findUserProfileByUserId(((User) authentication.getPrincipal()).getId());

        return leaderboardService.getFriendLeaderBoard(profile, limit);
    }
}
