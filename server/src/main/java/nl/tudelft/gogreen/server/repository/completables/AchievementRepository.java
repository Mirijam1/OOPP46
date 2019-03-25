package nl.tudelft.gogreen.server.repository.completables;

import nl.tudelft.gogreen.server.models.completables.Achievement;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    Collection<Achievement> findAchievementsByTrigger(Trigger trigger);
}
