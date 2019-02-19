package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByActivityname(@Param("activityname") String ActivityName);
}
