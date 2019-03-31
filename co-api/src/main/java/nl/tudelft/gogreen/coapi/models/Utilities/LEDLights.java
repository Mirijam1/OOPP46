package nl.tudelft.gogreen.coapi.models.Utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LEDLights {
    private Integer bulbsReplaced;
    private String country;
    private String key;

    public static void main(String[] args) throws Exception {
        LEDLights a = new LEDLights(2);
        System.out.println(XtoJson(a));
    }

    public LEDLights(@JsonProperty("energy") Integer bulbsReplaced) {
        this.bulbsReplaced = bulbsReplaced;
        this.country = "NL";
        this.key = "key";
    }

    public static String XtoJson(LEDLights a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }

}
