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
public class Category {
    private String id;
    private String categoryName;
    private String description;
    @Override
    public String toString(){
        return "id "+id+"\ncategoryName: "+categoryName+"\ndescription:"+description;
    }

    public String getcategoryName() {
        return this.categoryName;
    }
}
