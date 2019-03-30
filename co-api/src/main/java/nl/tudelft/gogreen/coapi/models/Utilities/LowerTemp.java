package nl.tudelft.gogreen.coapi.models.Utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class LowerTemp{
    private Integer residents;
    private Integer degrees;
    private double green_electricity;
    private String key;

    public static void main(String[] args) throws Exception {
        LowerTemp a = new LowerTemp(3, 4);
        System.out.println(XtoJson(a));
    }
    @JsonCreator
    public LowerTemp(Integer residents, Integer degrees) {
        this.residents=residents;
        this.degrees = degrees;
        this.green_electricity = (0.35 + 0.01 * degrees);
        this.key = "key";
    }

    public static String XtoJson(LowerTemp a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }
}
