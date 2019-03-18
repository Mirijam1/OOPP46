package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import org.springframework.stereotype.Service;

@Service
public class CarbonService implements ICarbonService {
    @Override
    public Float fetchPoints(CompletedActivity completedActivity) {
        //TODO: Implement
        return 1F;
    }
}
