package nl.tudelft.gogreen.shared.models;

import lombok.*;

import java.util.Collection;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class UserServer {
    private UUID id;
    private String username;
    private String password;
    private boolean expired;
    private boolean locked;
    private boolean enabled;
    private Collection<Authority> authorities;
    public boolean isAccountNonExpired;
    public boolean isAccountNonLocked;
    public boolean credentialsNonExpired;

}
