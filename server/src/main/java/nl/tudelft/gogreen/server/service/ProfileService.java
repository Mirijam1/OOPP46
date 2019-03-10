package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Badge;
<<<<<<< HEAD
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
=======
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import nl.tudelft.gogreen.server.repository.activity.CompletedActivityRepository;
import nl.tudelft.gogreen.server.service.handlers.IAchievementCheckService;
import nl.tudelft.gogreen.server.service.handlers.IBadgeCheckService;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
>>>>>>> dev
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> dev
import java.util.Collection;
import java.util.UUID;

@Service
public class ProfileService implements IProfileService {
    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;
<<<<<<< HEAD

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
=======
    private final IActivityService activityService;
    private final CompletedActivityRepository completedActivityRepository;
    private final IAchievementCheckService achievementCheckService;
    private final IBadgeCheckService badgeCheckService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          IActivityService activityService,
                          CompletedActivityRepository completedActivityRepository,
                          IAchievementCheckService achievementCheckService,
                          IBadgeCheckService badgeCheckService) {
        this.profileRepository = profileRepository;
        this.activityService = activityService;
        this.completedActivityRepository = completedActivityRepository;
        this.achievementCheckService = achievementCheckService;
        this.badgeCheckService = badgeCheckService;
>>>>>>> dev
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Badge> findBadges(UserProfile profile) {
<<<<<<< HEAD
        logger.info("Loading badges for profile " + profile.getUuid());
        UserProfile loadedProfile = profileRepository.findOneUserProfileByUuid(profile.getUuid());

        //TODO: Add edge case handling

        return loadedProfile.getBadges();
=======
        return null;
>>>>>>> dev
    }

    @Override
    public UserProfile createUserProfileForUser(User user) {
        logger.info("Create user profile for user " + user.getUsername());

        UserProfile userProfile = UserProfile.builder()
            .userID(user.getId())
<<<<<<< HEAD
            .badges(new ArrayList<>())
=======
            .points(0F)
>>>>>>> dev
            .uuid(UUID.randomUUID())
            .build();

        profileRepository.save(userProfile);
        return userProfile;
    }

    @Override
<<<<<<< HEAD
    public UserProfile getUserProfile(User user) {
        logger.info("Looking for profile of user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        UserProfile profile = profileRepository.findOneUserProfileByUserID(user.getId());
=======
    @Transactional(readOnly = true)
    public UserProfile getUserProfile(User user) {
        logger.info("Looking for profile of user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        UserProfile profile = profileRepository.findUserProfileByUserID(user.getId());
>>>>>>> dev

        if (profile != null) {
            logger.info("Found profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
            return profile;
        }

        logger.warn("Could not find a profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        // TODO: Misleading exception, make a better one later
        throw new UsernameNotFoundException("No profile found for user " + user.getUsername());
    }

    @Override
<<<<<<< HEAD
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
=======
    @Transactional
    public SubmitResponse submitActivity(SubmittedActivity submittedActivity, User user) {
        UserProfile profile = profileRepository.findUserProfileByUserID(user.getId());
        CompletedActivity activity = activityService.buildCompletedActivity(submittedActivity, profile);

        //TODO: Badge and achievement triggers

        completedActivityRepository.save(activity);

        profile.setPoints(profile.getPoints() + activity.getPoints());
        profileRepository.save(profile);

        SubmitResponse submitResponse = SubmitResponse.builder()
            .response("Ok")
            .points(activity.getPoints())
            .updatedPoints(profile.getPoints())
            .externalId(activity.getExternalId()).build();

        // Put through badge and achievement service
        badgeCheckService.checkBadge(activity, profile, submitResponse);
        achievementCheckService.checkAchievements(activity, profile, submitResponse);

        return submitResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CompletedActivity> getCompletedActivities(User user, Integer limit) {
        UserProfile profile = profileRepository.findUserProfileByUserID(user.getId());

        return completedActivityRepository
            .findCompletedActivitiesByProfileOrderByDateTimeCompletedDesc(profile, PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public CompletedActivity getCompletedActivityDetailed(User user, UUID externalId) {
        UserProfile profile = profileRepository.findUserProfileByUserID(user.getId());
        CompletedActivity activity = completedActivityRepository
            .findCompletedActivityByProfileAndExternalId(profile, externalId);

        if (activity != null) {
            Hibernate.initialize(activity.getOptions());
            Hibernate.initialize(activity.getActivity());
            return activity;
        }

        return null;
>>>>>>> dev
    }
}
