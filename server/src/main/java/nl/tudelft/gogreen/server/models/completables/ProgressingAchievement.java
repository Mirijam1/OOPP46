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
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "PROGRESSING_ACHIEVEMENT",
        uniqueConstraints = @UniqueConstraint(columnNames = {"profile", "achievement"}))
@EqualsAndHashCode(exclude = {"activity", "progress", "completed", "dateTimeAchieved", "activity", "profile"})
@ToString(exclude = {"activity", "profile"})
public class ProgressingAchievement {
    @JsonIgnore
    @Id
    @Column(name = "ID", unique = true, updatable = false, nullable = false)
    private UUID id;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "EXTERNAL_ID", updatable = false, nullable = false)
    private UUID externalId;

    @Column(name = "PROGRESS", nullable = false)
    private Integer progress;

    @Column(name = "COMPLETED", nullable = false)
    private Boolean completed;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "PROFILE", referencedColumnName = "ID")
    private UserProfile profile;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACHIEVEMENT", referencedColumnName = "ID")
    private Achievement achievement;

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
     * <p>Returns a {@link nl.tudelft.gogreen.shared.models.Achievement} instance of this achievement.</p>
     *
     * @return A {@link nl.tudelft.gogreen.shared.models.Achievement} instance
     */
    public nl.tudelft.gogreen.shared.models.Achievement toSharedModel() {
        return nl.tudelft.gogreen.shared.models.Achievement.builder()
                .achievedMessage(achievement.getAchievedMessage())
                .achievementName(achievement.getAchievementName())
                .completed(completed)
                .progress(progress)
                .description(achievement.getDescription())
                .requiredProgress(achievement.getRequiredTriggers())
                .externalId(externalId).build();
    }
}
