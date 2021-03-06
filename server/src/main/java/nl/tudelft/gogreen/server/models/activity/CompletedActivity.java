package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.tudelft.gogreen.server.models.activity.config.ConfiguredOption;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.completables.Trigger;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "COMPLETED_ACTIVITY")
@EqualsAndHashCode(exclude = "profile")
@ToString(exclude = "profile")
public class CompletedActivity {
    @JsonIgnore
    @Id
    @Column(name = "ID", unique = true, updatable = false, nullable = false)
    private UUID id;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "EXTERNAL_ID", updatable = false, nullable = false)
    private UUID externalId;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "PROFILE", referencedColumnName = "ID")
    private UserProfile profile;

    @Transient
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    private String username;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private Activity activity;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activity", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<AchievedBadge> achievedBadges;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activity", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<ProgressingAchievement> progressingAchievements;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.activity", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<ConfiguredOption> options;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "POINTS", nullable = false)
    private Float points;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "DATE_TIME_COMPLETED")
    private LocalDateTime dateTimeCompleted;

    @JsonIgnore
    @Transient
    private Collection<Trigger> triggers;
}
