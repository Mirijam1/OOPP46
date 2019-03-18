package nl.tudelft.gogreen.server.models.completables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "ACHIEVED_BADGE")
@EqualsAndHashCode(exclude = "activity")
@ToString(exclude = "activity")
public class AchievedBadge {
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
    @JoinColumn(name = "PROFILE", referencedColumnName = "PROFILE_ID")
    private UserProfile profile;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BADGE", referencedColumnName = "ID")
    private Badge badge;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private CompletedActivity activity;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "DATE_TIME_ACHIEVED")
    private LocalDateTime dateTimeAchieved;

    /**
     * <p>Returns a {@link nl.tudelft.gogreen.shared.models.Badge} instance of this badge.</p>
     *
     * @return A {@link nl.tudelft.gogreen.shared.models.Badge} instance
     */
    public nl.tudelft.gogreen.shared.models.Badge toSharedModel() {
        return nl.tudelft.gogreen.shared.models.Badge.builder()
                .achievedMessage(badge.getAchievedMessage())
                .badgeName(badge.getBadgeName())
                .externalId(externalId).build();
    }
}
