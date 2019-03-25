package nl.tudelft.gogreen.server.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @JsonIgnore
    @Id
    @Column(name = "PROFILE_ID", unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @JsonIgnore
    @Column(name = "USER_ID", unique = true, nullable = false, updatable = false)
    private UUID userID;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "POINTS", nullable = false)
    private Float points;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<CompletedActivity> activities;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<AchievedBadge> badges;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<ProgressingAchievement> achievements;
}
