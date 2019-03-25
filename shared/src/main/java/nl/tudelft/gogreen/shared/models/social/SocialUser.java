package nl.tudelft.gogreen.shared.models.social;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SocialUser {
    private String username;
    private Collection<Friendship> friendships;
}
