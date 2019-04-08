package nl.tudelft.gogreen.server.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.tudelft.gogreen.server.models.Authority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "USER_TABLE")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(exclude = {"profile"})
@ToString(exclude = {"profile"})
public class User implements UserDetails, Serializable {
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Id
    @Column(name = "USER_ID", nullable = false, unique = true, updatable = false)
    private UUID id;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
            nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
    @Column(name = "EXTERNAL_ID", nullable = false, unique = true, updatable = false)
    private UUID externalId;

    @JsonIgnore
    @OneToOne(targetEntity = UserProfile.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private UserProfile profile;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
            nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
    @Column(name = "NAME", nullable = false, unique = true)
    private String username;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "MAIL", unique = true)
    private String mail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "ACC_EXPIRED", nullable = false)
    private boolean expired;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "ACC_LOCKED", nullable = false)
    private boolean locked;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "ACC_ACTIVATED", nullable = false)
    private boolean enabled;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
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

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired();
    }

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    /*
    Maybe implement this later, for now the credentials never expire
     */
    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
