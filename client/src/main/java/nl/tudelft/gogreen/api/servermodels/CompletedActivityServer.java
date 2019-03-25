package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import nl.tudelft.gogreen.shared.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletedActivityServer {
    private UUID externalId;
    private ActivitySmall activitysmall;
    private Float points;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateTimeCompleted;
}
