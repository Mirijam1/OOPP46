package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.Achievement;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.completables.AchievementRepository;
import nl.tudelft.gogreen.server.repository.completables.ProgressingAchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AchievementCheckService implements IAchievementCheckService {
    private final AchievementRepository achievementRepository;
    private final ProgressingAchievementRepository progressingAchievementRepository;

    @Autowired
    public AchievementCheckService(AchievementRepository achievementRepository,
                                   ProgressingAchievementRepository progressingAchievementRepository) {
        this.achievementRepository = achievementRepository;
        this.progressingAchievementRepository = progressingAchievementRepository;
    }

    @Override
    public Set<UUID> checkAchievements(CompletedActivity completedActivity,
                                                         UserProfile userProfile,
                                                         Collection<Trigger> triggers) {
        Set<UUID> progressingAchievements = new HashSet<>();

        for (Trigger trigger : triggers) {
            Collection<Achievement> achievements = achievementRepository.findAchievementsByTrigger(trigger);

            for (Achievement achievement : achievements) {
                ProgressingAchievement progressingAchievement = progressingAchievementRepository
                        .findProgressingAchievementByProfileAndAchievement(userProfile, achievement);

                if (progressingAchievement == null) {
                    progressingAchievement = ProgressingAchievement.builder()
                            .achievement(achievement)
                            .completed(false)
                            .id(UUID.randomUUID())
                            .externalId(UUID.randomUUID())
                            .profile(userProfile)
                            .progress(0)
                            .build();
                }

                if (progressingAchievement.getCompleted()) {
                    continue;
                }

                progressingAchievement.setProgress(progressingAchievement.getProgress() + 1);

                if (progressingAchievement.getProgress().equals(achievement.getRequiredTriggers())) {
                    progressingAchievement.setCompleted(true);
                    progressingAchievement.setActivity(completedActivity);
                    progressingAchievement.setDateTimeAchieved(LocalDateTime.now());
                }

                progressingAchievementRepository.save(progressingAchievement);
                progressingAchievements.add(progressingAchievement.getId());
            }
        }

        return progressingAchievements;
    }
}
