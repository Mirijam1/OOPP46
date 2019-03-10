package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Authority;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.AuthorityRepository;
import nl.tudelft.gogreen.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
@Primary
public class UserDetailService implements UserDetailsService, IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserDetailService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
<<<<<<< HEAD
    private final ProfileService profileService;
=======
    private final IProfileService profileService;
>>>>>>> dev

    @Autowired
    public UserDetailService(UserRepository userRepository,
                             PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository,
<<<<<<< HEAD
                             ProfileService profileService) {
=======
                             IProfileService profileService) {
>>>>>>> dev
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.profileService = profileService;
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Looking for user with name: " + username);
<<<<<<< HEAD
        User user = this.userRepository.findByUsername(username);
=======
        User user = this.userRepository.findUserByUsername(username);
>>>>>>> dev

        // Return user if found
        if (user != null) {
            logger.info("Found user with name '" + username + "' and ID '" + user.getId() + "'");
            return user;
        }

        logger.warn("Could not find user with name: " + username);
        throw new UsernameNotFoundException(username);
    }

    @Override
    @Transactional
    public User createUser(String username, String password) {
        Collection<Authority> authorities = new ArrayList<>();

        // Add basic authority
        authorities.add(authorityRepository.findByName("USER_AUTHORITY"));

        User user = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .enabled(true)
            .id(UUID.randomUUID())
            .authorities(authorities)
            .locked(false)
            .expired(false)
            .build();

        // Create profile
        profileService.createUserProfileForUser(user);

        logger.info("Saving created user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User updatedUser, User user) {
        logger.info("Updating user '" + user.getUsername() + "' with ID '" + user.getId() + "'");

        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        logger.info("Deleting created user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        userRepository.delete(user);
    }
}
