package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.Badge;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.server.repository.completables.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class BadgeCheckService implements IBadgeCheckService {
    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeCheckService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Override
    @Transactional
    public Collection<AchievedBadge> checkBadge(CompletedActivity completedActivity,
                                                UserProfile userProfile,
                                                Collection<Trigger> triggers) {
        addSpecialBadges(completedActivity, userProfile, triggers);

        Collection<Badge> badges = badgeRepository.findBadgesByTriggerIn(triggers);
        Collection<AchievedBadge> achievedBadges = new ArrayList<>();

        for (Badge badge : badges) {
            AchievedBadge achievedBadge = AchievedBadge.builder()
                    .badge(badge)
                    .id(UUID.randomUUID())
                    .externalId(UUID.randomUUID())
                    .profile(userProfile)
                    .dateTimeAchieved(LocalDateTime.now())
                    .activity(completedActivity)
                    .build();

            achievedBadges.add(achievedBadge);
        }

        return achievedBadges;
    }

    private void addSpecialBadges(CompletedActivity completedActivity,
                                  UserProfile userProfile,
                                  Collection<Trigger> triggers) {
        //TODO: Add stuff like points gained etc
    }
}
