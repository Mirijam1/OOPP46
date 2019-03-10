package nl.tudelft.gogreen.server.repository.activity;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByActivityName(String activityName);

    Collection<Activity> findByCategory(Category category);

    Activity findActivityById(Integer activityId);
}
