package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;
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

    @Autowired
    public SocialService(FriendshipConnectionRepository friendshipConnectionRepository,
                         CompletedActivityRepository completedActivityRepository) {
        this.friendshipConnectionRepository = friendshipConnectionRepository;
        this.completedActivityRepository = completedActivityRepository;
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

        return new ServerError("Ok");
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

    @Transactional
    public FriendshipConnection acceptInvitation(FriendshipConnection friendshipConnection) {
        friendshipConnection.setAccepted(true);
        friendshipConnection.setAcceptedAt(LocalDateTime.now());
        friendshipConnection.setFriendShipStartedAt(LocalDateTime.now());

        return friendshipConnectionRepository.save(friendshipConnection);
    }
}