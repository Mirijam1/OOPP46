package nl.tudelft.gogreen.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Achievement {
    private UUID externalId;
    private String achievementName;
    private String achievedMessage;
    private String description;
    private Integer progress;
    private Integer requiredProgress;
    private Boolean completed;
}
