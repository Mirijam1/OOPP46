package nl.tudelft.gogreen.server.tests.models;

import nl.tudelft.gogreen.server.models.social.FriendshipConnection;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.social.Friendship;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class FriendshipConnectionTest {
    private UserProfile initProfile;
    private UserProfile invitedProfile;
    private FriendshipConnection connection;

    @Before
    public void setUp() {
        User initUser = new User(null, null, null, "user1", null, null, false, false, true, null);
        User invitedUser = new User(null, null, null, "user2", null, null,false, false, true, null);
        initProfile = new UserProfile(null, initUser, null, null, null, null, null, null);
        invitedProfile = new UserProfile(null, invitedUser, null, null, null, null, null, null);

        connection = FriendshipConnection
                .builder()
                .accepted(false)
                .acceptedAt(null)
                .externalId(UUID.randomUUID())
                .friendShipStartedAt(null)
                .invitedAt(LocalDateTime.now())
                .startUser(initProfile)
                .invitedUser(invitedProfile)
                .build();
    }

    @Test
    public void shouldReturnCorrectSharedModelWithInitiator() {
        Friendship sharedModel = connection.toSharedModel(true);

        assertEquals(sharedModel.getAccepted(), connection.getAccepted());
        assertEquals(sharedModel.getAcceptedAt(), connection.getAcceptedAt());
        assertEquals(sharedModel.getExternalId(), connection.getExternalId());
        assertEquals(sharedModel.getFriendShipStarted(), connection.getFriendShipStartedAt());
        assertEquals(sharedModel.getInvited(), connection.getInvitedAt());
        assertEquals(sharedModel.getFriend().getUsername(), initProfile.getUser().getUsername());
    }

    @Test
    public void shouldReturnCorrectSharedModelWithInvited() {
        Friendship sharedModel = connection.toSharedModel(false);

        assertEquals(sharedModel.getAccepted(), connection.getAccepted());
        assertEquals(sharedModel.getAcceptedAt(), connection.getAcceptedAt());
        assertEquals(sharedModel.getExternalId(), connection.getExternalId());
        assertEquals(sharedModel.getFriendShipStarted(), connection.getFriendShipStartedAt());
        assertEquals(sharedModel.getInvited(), connection.getInvitedAt());
        assertEquals(sharedModel.getFriend().getUsername(), invitedProfile.getUser().getUsername());
    }
}
