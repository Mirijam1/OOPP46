package nl.tudelft.gogreen.server.tests.models;

import nl.tudelft.gogreen.server.models.completables.Achievement;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ProgressingAchievementTest {
    @Test
    public void shouldReturnCorrectSharedModel() {
        Achievement originalAchievement = Achievement
                .builder()
                .achievedMessage("message")
                .achievementName("name")
                .description("desc")
                .requiredTriggers(42)
                .build();
        ProgressingAchievement achievement = ProgressingAchievement
                .builder()
                .achievement(originalAchievement)
                .completed(false)
                .progress(0)
                .externalId(UUID.randomUUID())
                .build();

        nl.tudelft.gogreen.shared.models.Achievement sharedModel = achievement.toSharedModel();

        assertEquals(sharedModel.getAchievedMessage(), originalAchievement.getAchievedMessage());
        assertEquals(sharedModel.getAchievementName(), originalAchievement.getAchievementName());
        assertEquals(sharedModel.getDescription(), originalAchievement.getDescription());
        assertEquals(sharedModel.getRequiredProgress(), originalAchievement.getRequiredTriggers());
        assertEquals(sharedModel.getCompleted(), achievement.getCompleted());
        assertEquals(sharedModel.getProgress(), achievement.getProgress());
        assertEquals(sharedModel.getExternalId(), achievement.getExternalId());
    }
}
