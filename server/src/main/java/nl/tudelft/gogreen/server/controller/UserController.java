package nl.tudelft.gogreen.server.controller;

import nl.tudelft.gogreen.server.exceptions.ForbiddenException;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.Authority;
import nl.tudelft.gogreen.server.models.User;
import nl.tudelft.gogreen.server.repository.AuthorityRepository;
import nl.tudelft.gogreen.server.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserDetailService userService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDetailService userService, AuthorityRepository authorityRepository,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getDetails(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        return (User) authentication.getPrincipal();
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User createUser(@RequestBody User user,  Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            throw new ForbiddenException();
        }

        //TODO: Make this prettier, iel
        List<Authority> authorities = new ArrayList<>();

        authorities.add(authorityRepository.findByName("USER_AUTHORITY"));
        user.setAuthorities(authorities);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.createUser(user);
    }

    @RequestMapping(path =  "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<String, String> deleteUser(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        userService.removeUser((User) authentication.getPrincipal());

        // TODO: Find something better than static call
        SecurityContextHolder.clearContext();

        return Collections.singletonMap("success", "true");
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody User updateUser(@RequestBody User user,  Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException();
        }

        //TODO: Add checks
        User loadedUser = (User) authentication.getPrincipal();

        user.setId(loadedUser.getId());
        user.setAuthorities(loadedUser.getAuthorities());

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userService.updateUser(user);
    }
}
