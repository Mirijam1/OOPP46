package nl.tudelft.gogreen.shared.models.social;

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
@EqualsAndHashCode(exclude = {"friend"})
@ToString(exclude = "friend")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Friendship {
    private UUID externalId;
    private SocialUser friend;
    private Boolean accepted;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime invited;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime acceptedAt;
    private @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime friendShipStarted;
}
