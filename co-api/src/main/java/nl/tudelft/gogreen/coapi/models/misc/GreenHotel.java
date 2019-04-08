package nl.tudelft.gogreen.coapi.models.misc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter

public class GreenHotel {
    @JsonProperty("room_nights")
    private Integer nights;
    private String key;



    @JsonCreator
    public GreenHotel(@JsonProperty("room_nights") Integer nights) {
        this.nights = nights;
        this.key = "key";
    }

    public static String jsonmaker(GreenHotel activity) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity);
    }
}
