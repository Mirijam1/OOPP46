package nl.tudelft.gogreen.coapi.models.Transportation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class train {
    private String country;
    private String Date;
    private Integer distance;

    public static void main(String[] args) throws Exception {
        train a = new train("2019-03-2019", 500);
        System.out.println(XtoJson(a));
    }
    @JsonCreator
    public train(String date, Integer distance) {
        this.Date = date;
        this.distance = distance;
        this.country = "NL";
    }

    public static String XtoJson(train a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }
}
