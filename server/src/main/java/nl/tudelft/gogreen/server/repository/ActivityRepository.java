package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    Activity findByActivityName(@Param("activityname") String activityName);
}
