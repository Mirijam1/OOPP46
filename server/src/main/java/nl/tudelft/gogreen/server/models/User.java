package nl.tudelft.gogreen.server.models;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
/*
Very basic class, will be developed further
 */

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "USER_TABLE")
public class User implements UserDetails, Serializable {
    @Id
    @Column(name = "UID", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACC_EXPIRED", nullable = false)
    private boolean expired;

    @Column(name = "ACC_LOCKED", nullable = false)
    private boolean locked;

    @Column(name = "ACC_ACTIVATED", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @OrderBy
    @JoinTable(
        name = "USER_AUTHORITIES",
        joinColumns = @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "UID"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "AUTHORITY_ID",
            referencedColumnName = "AID"
        )
    )
    private Collection<Authority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    /*
    Maybe implement this later, for now the credentials never expire
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
