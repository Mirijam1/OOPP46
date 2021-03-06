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
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCompletedActivity {
    private UUID externalID;
    private SubmittedActivity activity;
    private AchievedBadge achievedBadge;
    private Object[] options;
    private String points;

    private @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTimeDeserializer localDateTimeDeserializer;
}
