package nl.tudelft.gogreen.server.service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;

public interface ICarbonService {
    Float fetchPoints(CompletedActivity completedActivity);

    Double getRemotePoints(JsonNode requestNode, CompletedActivity activity) throws UnirestException;
}
