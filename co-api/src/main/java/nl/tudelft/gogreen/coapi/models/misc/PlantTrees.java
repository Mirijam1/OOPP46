package nl.tudelft.gogreen.coapi.models.misc;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantTrees {
    private Integer trees;

    private String key;

    @JsonCreator
    public PlantTrees(Integer trees) {
        this.trees = trees;
        this.key = "key";
    }
}
