package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import nl.tudelft.gogreen.shared.LocalDateTimeDeserializer;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Achievement {
        private UUID id;
        private @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTimeDeserializer localDateTimeDeserializer;

}