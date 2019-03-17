package nl.tudelft.gogreen.server.tests.models;

import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.Badge;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AchievedBadgeTest {
    @Test
    public void shouldReturnSharedModel() {
        UUID id = UUID.randomUUID();
        String name = "Such";
        String message = "wow";

        Badge badge = Badge.builder().badgeName(name).achievedMessage(message).build();
        AchievedBadge achievedBadge = AchievedBadge.builder().externalId(id).badge(badge).build();
        nl.tudelft.gogreen.shared.models.Badge sharedBadge = achievedBadge.toSharedModel();

        assertEquals(id, sharedBadge.getExternalId());
        assertEquals(name, sharedBadge.getBadgeName());
        assertEquals(message, sharedBadge.getAchievedMessage());
    }
}
