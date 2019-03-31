package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import nl.tudelft.gogreen.server.repository.activity.CompletedActivityRepository;
import nl.tudelft.gogreen.server.repository.completables.AchievedBadgeRepository;
import nl.tudelft.gogreen.server.repository.completables.ProgressingAchievementRepository;
import nl.tudelft.gogreen.server.repository.social.FriendshipConnectionRepository;
import nl.tudelft.gogreen.server.service.handlers.IAchievementCheckService;
import nl.tudelft.gogreen.server.service.handlers.IBadgeCheckService;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.social.Friendship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfileService implements IProfileService {
    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private final ProfileRepository profileRepository;
    private final IActivityService activityService;
    private final CompletedActivityRepository completedActivityRepository;
    private final IAchievementCheckService achievementCheckService;
    private final IBadgeCheckService badgeCheckService;
    private final AchievedBadgeRepository achievedBadgeRepository;
    private final FriendshipConnectionRepository friendshipConnectionRepository;
    private final ProgressingAchievementRepository progressingAchievementRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          IActivityService activityService,
                          CompletedActivityRepository completedActivityRepository,
                          IAchievementCheckService achievementCheckService,
                          IBadgeCheckService badgeCheckService,
                          AchievedBadgeRepository achievedBadgeRepository,
                          FriendshipConnectionRepository friendshipConnectionRepository,
                          ProgressingAchievementRepository progressingAchievementRepository) {
        this.profileRepository = profileRepository;
        this.activityService = activityService;
        this.completedActivityRepository = completedActivityRepository;
        this.achievementCheckService = achievementCheckService;
        this.badgeCheckService = badgeCheckService;
        this.achievedBadgeRepository = achievedBadgeRepository;
        this.progressingAchievementRepository = progressingAchievementRepository;
        this.friendshipConnectionRepository = friendshipConnectionRepository;
    }

    @Override
    public UserProfile createUserProfileForUser(User user) {
        logger.info("Create user profile for user " + user.getUsername());

        return UserProfile.builder()
                .user(user)
                .points(0F)
                .badges(new ArrayList<>())
                .achievements(new ArrayList<>())
                .friendsAsInitiator(new ArrayList<>())
                .friendsAsInvitedUser(new ArrayList<>())
                .activities(new ArrayList<>())
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile getUserProfile(User user) {
        logger.info("Looking for profile of user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());

        if (profile != null) {
            logger.info("Found profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
            return profile;
        }

        logger.warn("Could not find a profile for user '" + user.getUsername() + "' with ID '" + user.getId() + "'");
        throw new UsernameNotFoundException("No profile found for user " + user.getUsername());
    }

    @Override
    @Transactional
    public SubmitResponse submitActivity(SubmittedActivity submittedActivity, User user) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());
        CompletedActivity activity = activityService.buildCompletedActivity(submittedActivity, profile);

        completedActivityRepository.save(activity);

        profile.setPoints(profile.getPoints() + activity.getPoints());

        SubmitResponse submitResponse = SubmitResponse.builder()
                .response("Ok")
                .points(activity.getPoints())
                .updatedPoints(profile.getPoints())
                .externalId(activity.getExternalId())
                .badges(new ArrayList<>())
                .achievements(new ArrayList<>())
                .build();

        Collection<Trigger> triggers = activity.getTriggers();

        // Put through badge and achievement service
        Collection<AchievedBadge> achievedBadges = badgeCheckService
                .checkBadge(activity, profile, triggers);
        Set<UUID> progressingAchievements = achievementCheckService
                .checkAchievements(activity, profile, triggers);

        // Add badges + achievements
        achievedBadges.forEach(badge -> {
            addBadge(profile, badge);
            submitResponse.getBadges().add(badge.toSharedModel());
            achievedBadgeRepository.save(badge);
        });

        profileRepository.save(profile);

        progressingAchievements.forEach(uuid -> {
            ProgressingAchievement progressingAchievement = progressingAchievementRepository
                    .findProgressingAchievementById(uuid);

            activity.getProgressingAchievements().add(progressingAchievement);
            addAchievement(profile, progressingAchievement);
            submitResponse.getAchievements().add(progressingAchievement.toSharedModel());
        });

        // Set badges + achievements
        activity.setAchievedBadges(achievedBadges);

        completedActivityRepository.save(activity);
        profileRepository.save(profile);

        return submitResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CompletedActivity> getCompletedActivities(User user, Integer limit) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());

        return completedActivityRepository
                .findCompletedActivitiesByProfileOrderByDateTimeCompletedDesc(profile, PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public CompletedActivity getCompletedActivityDetailed(User user, UUID externalId) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());

        return completedActivityRepository
                .findCompletedActivityByProfileAndExternalId(profile, externalId);
    }

    @Override
    @Transactional
    public void addBadge(UserProfile user, AchievedBadge badge) {
        user.getBadges().add(badge);
    }

    @Override
    @Transactional
    public void addAchievement(UserProfile user, ProgressingAchievement achievement) {
        user.getAchievements().remove(achievement);
        user.getAchievements().add(achievement);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AchievedBadge> getAchievedBadges(User user) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());

        return achievedBadgeRepository.findAchievedBadgesByProfileOrderByDateTimeAchievedDesc(profile);
    }

    @Override
    public Collection<Friendship> getFriends(User user) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());
        Collection<Friendship> result = new ArrayList<>();

        friendshipConnectionRepository.findFriendshipConnectionsByStartUserAndAccepted(profile, true)
                .forEach(connection -> result.add(connection.toSharedModel(false)));
        friendshipConnectionRepository.findFriendshipConnectionsByInvitedUserAndAccepted(profile, true)
                .forEach(connection -> result.add(connection.toSharedModel(true)));

        return result;
    }

    @Override
    public Collection<Friendship> getPendingFriends(User user) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());
        Collection<Friendship> result = new ArrayList<>();

        friendshipConnectionRepository.findFriendshipConnectionsByStartUserAndAccepted(profile, false)
                .forEach(connection -> result.add(connection.toSharedModel(false)));
        return result;
    }

    @Override
    public Collection<Friendship> getInvitingFriends(User user) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());
        Collection<Friendship> result = new ArrayList<>();

        friendshipConnectionRepository.findFriendshipConnectionsByInvitedUserAndAccepted(profile, false)
                .forEach(connection -> result.add(connection.toSharedModel(true)));
        return result;
    }

    @Override
    public Collection<ProgressingAchievement> getAchievedAchievements(User user) {
        return getProgressingAchievements(user, true);
    }

    @Override
    public Collection<ProgressingAchievement> getProgressingAchievements(User user) {
        return getProgressingAchievements(user, false);
    }

    private Collection<ProgressingAchievement> getProgressingAchievements(User user, Boolean completed) {
        UserProfile profile = profileRepository.findUserProfileByUserId(user.getId());

        return progressingAchievementRepository
                .findProgressingAchievementByProfileAndCompletedOrderByDateTimeAchievedDesc(profile, completed);
    }
}