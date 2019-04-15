package nl.tudelft.gogreen.coapi.models.utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class SolarPanels {
    private Integer residents;
    @JsonProperty("green_electricity")
    private double electricity;
    private String key;

    /**
     * constructor solar panels activity.
     * @param residents number of residents.
     */
    @JsonCreator
    public SolarPanels(Integer residents) {
        this.residents = residents;
        this.electricity = 0.7;
        this.key = "key";
    }

    public static String jsonmaker(SolarPanels activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }

}
