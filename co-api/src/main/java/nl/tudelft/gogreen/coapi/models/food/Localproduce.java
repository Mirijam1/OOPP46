package nl.tudelft.gogreen.coapi.models.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

@Getter
@Setter
public class Localproduce {
    @JsonProperty("end_date")
    private String enddate;
    @JsonProperty("start_date")
    private String startdate;
    private double proximity;
    private double intensity;
    private double size;
    private String key;

    /**
     * constructor for localproduce.
     * @param startdate startdate
     */
    @JsonCreator
    public Localproduce(@JsonProperty("date") String startdate) {
        this.startdate = startdate;
        this.enddate = LocalDate.parse(startdate).plusDays(30).toString();
        this.intensity = 0.1;
        this.proximity = 1;
        this.size = 2500;
        this.key = "key";
    }

    public static String jsonmaker(Localproduce activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }
}
