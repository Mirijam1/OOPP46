package nl.tudelft.gogreen.server.repository.completables;

import nl.tudelft.gogreen.server.models.completables.Badge;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    Collection<Badge> findBadgesByTrigger(Trigger trigger);
}
