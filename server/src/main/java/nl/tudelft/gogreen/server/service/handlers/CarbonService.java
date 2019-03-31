package nl.tudelft.gogreen.server.service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.activity.config.ConfiguredOption;
import nl.tudelft.gogreen.server.models.activity.config.InputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class CarbonService implements ICarbonService {
    private final Logger logger = LoggerFactory.getLogger(CarbonService.class);

    private final ObjectMapper mapper;

    @Autowired
    public CarbonService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Float fetchPoints(CompletedActivity completedActivity) {
        Collection<ConfiguredOption> options = completedActivity.getOptions();
        ObjectNode requestNode = mapper.createObjectNode();

        // Add date
        LocalDateTime dateTime = completedActivity.getDateTimeCompleted();
        String formattedDate = String.format("%1$d-%2$02d-%3$d",
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth());
        requestNode.put("date", formattedDate);

        // Add options
        for (ConfiguredOption option : options) {
            String value = option.getValue();

            // Round to integer for internal api
            if (option.getInputType() == InputType.FLOAT) {
                value = Float.valueOf(value).intValue() + "";
            }

            requestNode.put(option.getActivityOption().getInternalAPIName(), value);
        }

        try {
            logger.info("Sending request to internal API: " + requestNode);
            return this.getRemotePoints(requestNode, completedActivity).floatValue();
        } catch (UnirestException e) {
            logger.error("Could not fetch points from internal API, returning 20 points! Cause: " + e.getCause());
        }

        return 20F;
    }

    @Override
    public Double getRemotePoints(JsonNode requestNode, CompletedActivity activity) throws UnirestException {
        String url = String.format("http://localhost:8080/co-api/%1$s/%2$s",
                activity.getActivity().getCategory().getCategoryName().toLowerCase(),
                activity.getActivity().getInternalAPIName());
        HttpResponse<Double> response = Unirest.post(url)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestNode)
                .asObject(Double.class);

        if (response.getStatus() != 200) {
            logger.error("Could not fetch points from internal API, returning 20 points! Status code: "
                    + response.getStatus());
            return 20D;
        }

        return response.getBody();
    }
}
