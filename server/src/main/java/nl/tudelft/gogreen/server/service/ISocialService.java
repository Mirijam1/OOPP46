package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import java.util.Collection;
import java.util.UUID;

public interface ISocialService {
    FriendshipConnection addFriend(UserProfile user, UserProfile target);

    ServerError deleteFriend(UserProfile user, UserProfile target);

    Collection<CompletedActivity> findFriendActivities(UserProfile user, Integer limit, Boolean includeSelf);

    Collection<UserProfile> searchForUser(String name, UUID filter) throws InterruptedException;
}
