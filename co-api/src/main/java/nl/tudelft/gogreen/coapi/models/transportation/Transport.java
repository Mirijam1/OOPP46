package nl.tudelft.gogreen.coapi.models.transportation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class Transport {
    private String date;
    private Integer distance;
    private String key;


    /**
     * constructor for bus/car journey.
     * @param date date travelled.
     * @param distance distance travelled.
     */
    @JsonCreator
    //Same params for both car and bus
    public Transport(String date, Integer distance) {
        this.date = date;
        this.distance = distance;
        this.key = "key";
    }

    public static String jsonmaker(Transport activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }
}
