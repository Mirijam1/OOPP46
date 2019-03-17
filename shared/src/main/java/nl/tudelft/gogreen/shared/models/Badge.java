package nl.tudelft.gogreen.shared.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Badge {
    private UUID externalId;
    private String badgeName;
    private String achievedMessage;
}
