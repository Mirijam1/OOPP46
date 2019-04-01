package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.exceptions.InternalServerError;
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
        ArrayList<Trigger> workingTriggers = new ArrayList<>(triggers);
        Collection<AchievedBadge> achievedBadges = new ArrayList<>();

        addSpecialBadges(completedActivity, userProfile, workingTriggers);

        int loopGuard = 10000; // 10000 triggers per activity should be more than enough as upper limit

        while (!workingTriggers.isEmpty() && loopGuard != 0) {
            Trigger currentTrigger = workingTriggers.remove(workingTriggers.size() - 1);

            achievedBadges.addAll(addBadgesAndTriggers(completedActivity,
                    userProfile,
                    currentTrigger,
                    triggers,
                    workingTriggers));

            loopGuard -= 1;
        }

        if (loopGuard == 0) {
            // If this happened there is a circular reference between triggers, which should be fixed
            throw new InternalServerError();
        }

        return achievedBadges;
    }

    @Override
    @Transactional
    public Collection<AchievedBadge> addBadgesAndTriggers(CompletedActivity completedActivity,
                                                          UserProfile userProfile,
                                                          Trigger trigger,
                                                          Collection<Trigger> triggers,
                                                          Collection<Trigger> workingTriggers) {
        Collection<Badge> badges = badgeRepository.findBadgesByTrigger(trigger);
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
            triggers.addAll(badge.getTriggers());
            workingTriggers.addAll(badge.getTriggers());
        }

        return achievedBadges;
    }

    private void addSpecialBadges(CompletedActivity completedActivity,
                                  UserProfile userProfile,
                                  Collection<Trigger> triggers) {
        for (float points = completedActivity.getPoints(); points > 0 && triggers.size() < 100; points -= 1) {
            triggers.add(Trigger.GAINED_POINT);
        }
    }
}
