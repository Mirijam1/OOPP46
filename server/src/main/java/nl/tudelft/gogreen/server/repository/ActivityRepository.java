package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findByActivityName(String name);
}
