package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import nl.tudelft.gogreen.server.repository.activity.CompletedActivityRepository;
import nl.tudelft.gogreen.server.repository.social.FriendshipConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class SocialService implements ISocialService {
    private final FriendshipConnectionRepository friendshipConnectionRepository;
    private final CompletedActivityRepository completedActivityRepository;
    private final ISearchService searchService;
    private final ProfileRepository profileRepository;

    /**
     * All parameter constructor.
     * @param friendshipConnectionRepository the repository responsible for connections
     * @param completedActivityRepository the repository responsible for activities
     * @param searchService the service responsible for searching
     * @param profileRepository the repository responsible for profiles
     */
    @Autowired
    public SocialService(FriendshipConnectionRepository friendshipConnectionRepository,
                         CompletedActivityRepository completedActivityRepository, ISearchService searchService,
                         ProfileRepository profileRepository) {
        this.friendshipConnectionRepository = friendshipConnectionRepository;
        this.completedActivityRepository = completedActivityRepository;
        this.searchService = searchService;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public FriendshipConnection addFriend(UserProfile user, UserProfile target) {
        //TODO: Add blocking maybe?
        return buildConnection(user, target);
    }

    @Override
    public ServerError deleteFriend(UserProfile user, UserProfile target) {
        FriendshipConnection connection = friendshipConnectionRepository
                .findFriendshipConnectionByStartUserAndInvitedUser(user, target);
        FriendshipConnection connectionAsInvite = friendshipConnectionRepository
                .findFriendshipConnectionByStartUserAndInvitedUser(target, user);

        if (connection == null && connectionAsInvite == null) {
            throw new BadRequestException();
        } else if (connection == null) {
            friendshipConnectionRepository.delete(connectionAsInvite);
        } else {
            friendshipConnectionRepository.delete(connection);
        }

        return new ServerError("Ok", null);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CompletedActivity> findFriendActivities(UserProfile user,
                                                              Integer limit,
                                                              Boolean includeSelf) {
        Collection<UserProfile> profiles = new ArrayList<>();

        if (includeSelf) {
            profiles.add(user);
        }

        user.getFriendsAsInitiator().stream().filter(FriendshipConnection::getAccepted)
                .forEach(connection -> profiles.add(connection.getInvitedUser()));
        user.getFriendsAsInvitedUser().stream().filter(FriendshipConnection::getAccepted)
                .forEach(connection -> profiles.add(connection.getStartUser()));

        Collection<CompletedActivity> activities = completedActivityRepository
                .findCompletedActivitiesByProfileInOrderByDateTimeCompletedDesc(profiles, PageRequest.of(0, limit));
        activities.forEach(activity -> activity.setUsername(activity.getProfile().getUser().getUsername()));

        return activities;
    }

    @Override
    public Collection<UserProfile> searchForUser(String name, UUID filter) throws InterruptedException {
        return profileRepository.findUserProfilesByUserInAndUserIdIsNot(searchService.searchUsersByName(name), filter);
    }

    /**
     * buildConnection between two users accepting friendship.
     *
     * @param profile current user
     * @param target  target user.
     * @return FriendshipConnection
     */
    @Transactional
    public FriendshipConnection buildConnection(UserProfile profile, UserProfile target) {
        FriendshipConnection connection = friendshipConnectionRepository
                .findFriendshipConnectionByStartUserAndInvitedUser(profile, target);
        FriendshipConnection connectionAsInvite = friendshipConnectionRepository
                .findFriendshipConnectionByStartUserAndInvitedUser(target, profile);

        if (connection == null && connectionAsInvite == null) {
            return buildNewConnection(profile, target);
        } else if (connectionAsInvite == null) {
            //TODO: Find sensible code
            throw new BadRequestException();
        } else if (connectionAsInvite.getAccepted()) {
            //TODO: Idem
            throw new BadRequestException();
        }

        return acceptInvitation(connectionAsInvite);
    }

    /**
     * buildNewConnection between two users.
     *
     * @param profile current user.
     * @param target  target user.
     * @return FriendshipConnection
     */
    @Transactional
    public FriendshipConnection buildNewConnection(UserProfile profile, UserProfile target) {
        FriendshipConnection connection = FriendshipConnection
                .builder()
                .accepted(false)
                .id(UUID.randomUUID())
                .externalId(UUID.randomUUID())
                .invitedAt(LocalDateTime.now())
                .startUser(profile)
                .invitedUser(target)
                .build();

        return friendshipConnectionRepository.save(connection);
    }

    /**
     * accept user invitations.
     *
     * @param friendshipConnection connection betwee two users.
     * @return FriendshipConnection
     */
    @Transactional
    public FriendshipConnection acceptInvitation(FriendshipConnection friendshipConnection) {
        friendshipConnection.setAccepted(true);
        friendshipConnection.setAcceptedAt(LocalDateTime.now());
        friendshipConnection.setFriendShipStartedAt(LocalDateTime.now());

        return friendshipConnectionRepository.save(friendshipConnection);
    }
}
