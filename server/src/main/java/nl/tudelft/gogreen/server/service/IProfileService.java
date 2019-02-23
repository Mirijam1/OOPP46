package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.Badge;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import java.util.Collection;

public interface IProfileService {
    Collection<Badge> findBadges(UserProfile profile);

    UserProfile createUserProfileForUser(User user);

    UserProfile getUserProfile(User user);

    UserProfile getUserProfileEagerly(User user);
}
