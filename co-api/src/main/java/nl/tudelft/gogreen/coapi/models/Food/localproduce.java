package nl.tudelft.gogreen.coapi.models.Food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

@Getter
@Setter
public class localproduce {
    private String end_date;
    private String start_date;
    private double proximity;
    private double intensity;
    private double size;
    private String key;

    public static void main(String[] args) throws Exception {
        localproduce a = new localproduce("2019-03-29");
        System.out.println(XtoJson(a));
    }

     @JsonCreator
    public localproduce(@JsonProperty("date") String start_date) {
        this.start_date = start_date;
        this.end_date = LocalDate.parse(start_date).plusDays(30).toString();
        this.intensity = 0.1;
        this.proximity = 1;
        this.size = 2500;
        this.key = "key";
    }

    public static String XtoJson(localproduce a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }
}
