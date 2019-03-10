package nl.tudelft.gogreen.server.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonView;
>>>>>>> dev
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.Authority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails, Serializable {
<<<<<<< HEAD
=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
>>>>>>> dev
    @Id
    @Column(name = "USER_ID", nullable = false, unique = true, updatable = false)
    private UUID id;

<<<<<<< HEAD
=======
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class)
>>>>>>> dev
    @Column(name = "NAME", nullable = false, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", nullable = false)
    private String password;

<<<<<<< HEAD
    @Column(name = "ACC_EXPIRED", nullable = false)
    private boolean expired;

    @Column(name = "ACC_LOCKED", nullable = false)
    private boolean locked;

    @Column(name = "ACC_ACTIVATED", nullable = false)
    private boolean enabled;

=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
    @Column(name = "ACC_EXPIRED", nullable = false)
    private boolean expired;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
    @Column(name = "ACC_LOCKED", nullable = false)
    private boolean locked;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
    @Column(name = "ACC_ACTIVATED", nullable = false)
    private boolean enabled;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
>>>>>>> dev
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy
    @JoinTable(
        name = "USER_AUTHORITIES",
        joinColumns = @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "USER_ID"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "AUTHORITY_ID",
            referencedColumnName = "AID"
        )
    )
    private Collection<Authority> authorities;

<<<<<<< HEAD
=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
>>>>>>> dev
    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired();
    }

<<<<<<< HEAD
=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
>>>>>>> dev
    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    /*
    Maybe implement this later, for now the credentials never expire
     */
<<<<<<< HEAD
=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
        nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
>>>>>>> dev
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
