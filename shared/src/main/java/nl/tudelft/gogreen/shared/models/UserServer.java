package nl.tudelft.gogreen.shared.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class UserServer {
    private User user;
    public float points;

}
