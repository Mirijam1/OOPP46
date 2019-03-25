package nl.tudelft.gogreen.api.servermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String username;
    private String password;
    private Float points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return this.username;
    }

}
