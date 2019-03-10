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
public class SubmitResponse {
    private String response;
    private UUID externalId;
    private Float points;
    private Float updatedPoints;
    // TODO: Add badges and everything
}
