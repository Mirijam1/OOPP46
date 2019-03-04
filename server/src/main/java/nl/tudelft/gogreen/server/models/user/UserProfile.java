package nl.tudelft.gogreen.server.models.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.Badge;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true)
    private Collection<CompletedActivity> activities;
}
