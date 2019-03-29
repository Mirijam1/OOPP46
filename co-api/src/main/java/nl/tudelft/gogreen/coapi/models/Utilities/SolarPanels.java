package nl.tudelft.gogreen.coapi.models.Utilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class SolarPanels {
    private Integer residents;
    private String key;
    private double green_electricity;

    public static void main(String[] args) throws Exception {
        SolarPanels a = new SolarPanels(3);
        System.out.println(XtoJson(a));
    }

    @JsonCreator
    public SolarPanels(Integer residents) {
        this.residents = residents;
        this.green_electricity = 0.7;
        this.key = "key";
    }

    public static String XtoJson(SolarPanels a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }

}
