package nl.tudelft.gogreen.coapi.models.utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LowerTemp {
    private Integer residents;
    private Integer degrees;
    @JsonProperty("green_electricity")
    private double electricity;
    private String key;


    /**
     * lowertemp constructor for lowering temperature activity.
     * @param residents number of residents.
     * @param degrees degrees reduced.
     */
    @JsonCreator
    public LowerTemp(Integer residents, Integer degrees) {
        this.residents = residents;
        this.degrees = degrees;
        this.electricity = 0.35 + 0.01 * degrees;
        this.key = "key";
    }

    public static String jsonmaker(LowerTemp activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }
}
