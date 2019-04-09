package nl.tudelft.gogreen.server.exceptions.handling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerError {
    private String response;
    private String additionalInfo;
}
