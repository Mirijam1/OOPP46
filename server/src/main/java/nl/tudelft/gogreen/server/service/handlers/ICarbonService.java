package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;

public interface ICarbonService {
    Float fetchPoints(CompletedActivity completedActivity);
}
