package nl.tudelft.gogreen.coapi.models.Misc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class PlantTrees {
    private Integer trees;

    private String key;

    public static void main(String[] args) throws Exception {
    }

    @JsonCreator
    public PlantTrees(Integer trees) {
        this.trees = trees;
        this.key = "key";
    }

    public static String XtoJson(PlantTrees a) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(a);
    }
}
