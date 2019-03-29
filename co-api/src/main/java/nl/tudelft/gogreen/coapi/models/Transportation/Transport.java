package nl.tudelft.gogreen.coapi.models.Transportation;

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

    public static void main(String[] args) throws Exception {
        Transport a = new Transport("2019-03-2019", 500);
        System.out.println(XtoJson(a));
    }

    @JsonCreator
    //Same params for both car and bus
    public Transport(String date, Integer distance) {
        this.date = date;
        this.distance = distance;
        this.key = "key";
    }

    public static String XtoJson(Transport a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }
}
