package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import nl.tudelft.gogreen.api.LocalDateTimeDeserializer;
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
public class CompletedActivity {
    private UUID externalId;
    private Float points;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateTimeCompleted;
    private Activity activity;

    public String getActivityDescription(){
        return this.activity.getDescription();
    }
}
