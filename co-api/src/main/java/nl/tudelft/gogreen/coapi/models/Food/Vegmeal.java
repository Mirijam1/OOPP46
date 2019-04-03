package nl.tudelft.gogreen.coapi.models.Food;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

@Getter
@Setter
public class Vegmeal {
    private String start_date;
    private String end_date;
    private double intensity;
    private Integer size;
    private double vegetables_share = 0.2;
    private String key;

    public static void main(String[] args) throws Exception {
        Vegmeal a = new Vegmeal("2019-03-29", 1, 400);
        System.out.println(XtoJson(a));
    }

    //Eat a veg meal
    public Vegmeal(@JsonProperty("date") String start_date, Integer amount, Integer cal) {
        this.start_date = start_date;
        this.intensity = 4;
        this.end_date = LocalDate.parse(start_date).plusDays(1).toString();
        this.size = amount * cal;
        this.key = "key";
    }

    public static String XtoJson(Vegmeal a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }

}
