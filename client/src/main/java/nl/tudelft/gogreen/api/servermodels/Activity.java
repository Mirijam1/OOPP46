package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
<<<<<<< HEAD
import lombok.*;

@Getter
@Setter
=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

>>>>>>> dev
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private Integer id;
    private String activityName;
<<<<<<< HEAD
    private Category category;

    public String getcatName(){
        return this.category.getcategoryName();
    }

    @Override
    public String toString(){
        return "id "+id+"\nactivityName: "+activityName+"\npoints:";
    }
=======
    private String description;
    private Category category;
>>>>>>> dev
}
