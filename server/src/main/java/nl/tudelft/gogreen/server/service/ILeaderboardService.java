package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.user.UserProfile;

import java.util.Collection;

public interface ILeaderboardService {
    Collection<UserProfile> getGlobalLeaderBoard(int limit);

    Collection<UserProfile> getFriendLeaderBoard(UserProfile profile, int limit);
}
