package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private Integer id;
    private String activityName;
    private Category category;

    public String getcatName(){
        return this.category.getcategoryName();
    }

    @Override
    public String toString(){
        return "id "+id+"\nactivityName: "+activityName+"\npoints:";
    }
}
