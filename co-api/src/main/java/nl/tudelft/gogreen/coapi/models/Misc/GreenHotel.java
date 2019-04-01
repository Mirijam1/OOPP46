package nl.tudelft.gogreen.coapi.models.Misc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter

public class GreenHotel {
    private Integer nights;
    private String key;

    public static void main(String[] args) throws Exception {
        GreenHotel a = new GreenHotel(3);
        System.out.println(XtoJson(a));
    }

    public GreenHotel(@JsonProperty("room_nights") Integer nights) {
        this.nights = nights;
        this.key = "key";
    }

    public static String XtoJson(GreenHotel a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }
}
