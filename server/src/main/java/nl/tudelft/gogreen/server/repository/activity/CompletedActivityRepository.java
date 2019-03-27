package nl.tudelft.gogreen.server.repository.activity;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CompletedActivityRepository extends JpaRepository<CompletedActivity, UUID> {
    List<CompletedActivity> findCompletedActivitiesByProfileOrderByDateTimeCompletedDesc(UserProfile profile,
                                                                                         Pageable pageable);

    CompletedActivity findCompletedActivityByProfileAndExternalId(UserProfile profile, UUID externalId);

    CompletedActivity findCompletedActivityById(UUID id);

    List<CompletedActivity> findCompletedActivitiesByProfileInOrderByDateTimeCompletedDesc(
            Collection<UserProfile> profiles,
            Pageable pageable);
}
