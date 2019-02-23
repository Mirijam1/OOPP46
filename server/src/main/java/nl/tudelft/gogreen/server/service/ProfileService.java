package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Badge;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class ProfileService implements IProfileService {
    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Badge> findBadges(UserProfile profile) {
        logger.info("Loading badges for profile " + profile.getUuid());
        UserProfile loadedProfile = profileRepository.findOneUserProfileByUuid(profile.getUuid());

        //TODO: Add edge case handling

        return loadedProfile.getBadges();
    }

    @Override
    public UserProfile createUserProfileForUser(User user) {
        logger.info("Create user profile for user " + user.getUsername());

        UserProfile userProfile = UserProfile.builder()
            .userID(user.getId())
            .badges(new ArrayList<>())
            .uuid(UUID.randomUUID())
            .build();

        profileRepository.save(userProfile);
        return userProfile;
    }

    @Override
    public UserProfile getUserProfile(User user) {
        logger.info("Looking for profile of user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        UserProfile profile = profileRepository.findOneUserProfileByUserID(user.getId());

        if (profile != null) {
            logger.info("Found profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
            return profile;
        }

        logger.warn("Could not find a profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        // TODO: Misleading exception, make a better one later
        throw new UsernameNotFoundException("No profile found for user " + user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile getUserProfileEagerly(User user) {
        logger.info("[EAGER] Looking for profile of user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        UserProfile profile = profileRepository.findOneUserProfileByUserID(user.getId());

        if (profile != null) {
            logger.info("[EAGER} Found profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");

            // Triggering lazy loading
            profile.getBadges().size();

            return profile;
        }

        logger.warn("Could not find a profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        // TODO: Same as lazy method
        throw new UsernameNotFoundException("No profile found for user " + user.getUsername());
    }
}
