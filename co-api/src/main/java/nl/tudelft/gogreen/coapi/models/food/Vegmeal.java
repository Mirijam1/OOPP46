package nl.tudelft.gogreen.coapi.models.food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

@Getter
@Setter
public class Vegmeal {
    @JsonProperty("start_date")
    private String startdate;
    @JsonProperty("end_date")
    private String enddate;
    private double intensity;
    private Integer size;
    @JsonProperty("vegetables_share")
    private double vegetablesshare = 0.2;
    private String key;



    /**
     * constructor for submitting vegetarian meal.
     * @param startdate start date
     * @param amount number of meals
     * @param cal calories
     */
    public Vegmeal(@JsonProperty("date") String startdate, Integer amount, Integer cal) {
        this.startdate = startdate;
        this.intensity = 4;
        this.enddate = LocalDate.parse(startdate).plusDays(1).toString();
        this.size = amount * cal;
        this.key = "key";
    }

    public static String jsonmaker(Vegmeal activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }

}
