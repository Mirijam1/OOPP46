package nl.tudelft.gogreen.coapi.models.transportation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class Train {
    private String country;
    private String date;
    private Integer distance;



    /**
     * train constructor for submitting train journey activity.
     * @param date date travelled.
     * @param distance distance travelled.
     */
    @JsonCreator
    public Train(String date, Integer distance) {
        this.date = date;
        this.distance = distance;
        this.country = "NL";
    }

    public static String jsonmaker(Train activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }
}
