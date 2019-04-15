package nl.tudelft.gogreen.coapi.models.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LEDLights {
    private Integer energy;
    private Integer bulbsReplaced;
    private String country;
    private String key;

    /**
     * constructors for LEDLights activities.
     * @param bulbsReplaced number of bulbs replaced.
     */
    public LEDLights(@JsonProperty("energy") Integer bulbsReplaced) {
        this.energy = bulbsReplaced;
        this.country = "NL";
        this.key = "key";
    }

    public static String jsonmaker(LEDLights activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }

}
