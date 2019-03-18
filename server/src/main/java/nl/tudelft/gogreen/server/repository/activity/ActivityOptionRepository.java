package nl.tudelft.gogreen.server.repository.activity;

import nl.tudelft.gogreen.server.models.activity.Activity;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ActivityOptionRepository extends JpaRepository<ActivityOption, Integer> {
    Collection<ActivityOption> findActivityOptionsByActivity(Activity activity);

    ActivityOption findActivityOptionById(Integer optionId);
}
