package nl.tudelft.gogreen.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BasicResponse {
    private String username;
    private String password;
    private boolean tfaEnabled;
    private String mail;
    private Float points;
    private UUID externalId;

    /**
     * user constructor.
     * @param username username of user
     * @param password password of user
     * @param mail email of user
     */
    public User(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
}
