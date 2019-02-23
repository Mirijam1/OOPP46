package nl.tudelft.gogreen.server.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.Badge;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "USER_PROFILE")
public class UserProfile implements Serializable {
    @Id
    @Column(name = "PROFILE_ID", unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "USER_ID", unique = true, nullable = false, updatable = false)
    private UUID userID;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy
    @JoinTable(
        name = "USER_BADGES",
        joinColumns = @JoinColumn(
            name = "PROFILE_ID",
            referencedColumnName = "PROFILE_ID"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "BADGE_ID",
            referencedColumnName = "BADGE_ID"
        )
    )
    private Collection<Badge> badges;
}
