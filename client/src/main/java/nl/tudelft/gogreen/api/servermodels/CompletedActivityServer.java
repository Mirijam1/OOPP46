package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.tudelft.gogreen.shared.LocalDateTimeDeserializer;
import nl.tudelft.gogreen.shared.models.Activity;

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
    private Activity activity;
    private Float points;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime dateTimeCompleted;
}
