package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.activity.config.ConfiguredOption;
import nl.tudelft.gogreen.server.models.user.UserProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "COMPLETED_ACTIVITY")
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
    @JoinColumn(name = "PROFILE", referencedColumnName = "PROFILE_ID")
    private UserProfile profile;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private Activity activity;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.activity", orphanRemoval = true)
    private Collection<ConfiguredOption> options;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
        nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "POINTS", nullable = false)
    private Float points;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
        nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "DATE_TIME_COMPLETED")
    private LocalDateTime dateTimeCompleted;
}
