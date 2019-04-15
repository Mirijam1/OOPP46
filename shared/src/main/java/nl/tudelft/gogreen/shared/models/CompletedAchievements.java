package nl.tudelft.gogreen.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.shared.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletedAchievements {
    private UUID externalID;
    private Integer progress;
    private Achievement achievement;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dateTimeCompleted;

}
