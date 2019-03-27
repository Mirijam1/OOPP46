package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class LeaderboardService implements ILeaderboardService {
    private final ProfileRepository profileRepository;

    @Autowired
    public LeaderboardService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Collection<UserProfile> getGlobalLeaderBoard(int limit) {
        return profileRepository
                .findUserProfilesByPointsGreaterThanEqualOrderByPointsDesc(0F, PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserProfile> getFriendLeaderBoard(UserProfile profile, int limit) {
        Collection<UUID> friends = new ArrayList<>();

        profile.getFriendsAsInitiator().stream().filter(FriendshipConnection::getAccepted)
                .forEach(connection -> friends.add(connection.getInvitedUser().getId()));

        profile.getFriendsAsInvitedUser().stream().filter(FriendshipConnection::getAccepted)
                .forEach(connection -> friends.add(connection.getStartUser().getId()));

        return profileRepository.findUserProfilesByIdInOrIdOrderByPointsDesc(friends,
                profile.getId(),
                PageRequest.of(0, limit));
    }
}
