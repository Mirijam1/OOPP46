package nl.tudelft.gogreen.server.repository.social;

import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface FriendshipConnectionRepository extends JpaRepository<FriendshipConnection, UUID> {
    Collection<FriendshipConnection> findFriendshipConnectionsByStartUserAndAccepted(UserProfile profile,
                                                                                     Boolean accepted);

    Collection<FriendshipConnection> findFriendshipConnectionsByInvitedUserAndAccepted(UserProfile profile,
                                                                                       Boolean accepted);

    FriendshipConnection findFriendshipConnectionByStartUserAndInvitedUser(UserProfile profile, UserProfile target);
}
