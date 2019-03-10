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
public class Category {
    private String id;
    private String categoryName;
    private String description;
<<<<<<< HEAD
    @Override
    public String toString(){
        return "id "+id+"\ncategoryName: "+categoryName+"\ndescription:"+description;
    }

    public String getcategoryName() {
        return this.categoryName;
    }
=======
>>>>>>> dev
}
