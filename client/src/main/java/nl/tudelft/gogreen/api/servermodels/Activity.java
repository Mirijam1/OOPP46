package nl.tudelft.gogreen.api.servermodels;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Activity {
    private Integer id;
    private String activityName;
    private Category category;
    private double points;

    public String getcatName(){
        return this.category.getcategoryName();
    }

    @Override
    public String toString(){
        return "id "+id+"\nactivityName: "+activityName+"\npoints:"+points;
    }
}
