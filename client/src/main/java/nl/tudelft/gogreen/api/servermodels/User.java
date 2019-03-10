package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String password;
<<<<<<< HEAD
=======
    private Float points;
>>>>>>> dev
}
