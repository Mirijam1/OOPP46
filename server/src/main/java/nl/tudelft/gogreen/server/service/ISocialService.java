package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;

public interface ISocialService {
    FriendshipConnection addFriend(UserProfile user, UserProfile target);

    ServerError deleteFriend(UserProfile user, UserProfile target);
}
