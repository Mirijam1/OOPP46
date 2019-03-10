package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonManagedReference;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
>>>>>>> dev
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
<<<<<<< HEAD
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
=======
import java.time.LocalDateTime;
import java.util.Collection;
>>>>>>> dev
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "COMPLETED_ACTIVITY")
public class CompletedActivity {
<<<<<<< HEAD
=======
    @JsonIgnore
>>>>>>> dev
    @Id
    @Column(name = "ID", unique = true, updatable = false, nullable = false)
    private UUID id;

<<<<<<< HEAD
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "USER", referencedColumnName = "PROFILE_ID")
    private UserProfile profile;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private Activity activity;

=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
        nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "EXTERNAL_ID", updatable = false, nullable = false)
    private UUID externalId;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER", referencedColumnName = "PROFILE_ID")
    private UserProfile profile;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private Activity activity;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
>>>>>>> dev
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.activity", orphanRemoval = true)
    private Collection<ConfiguredOption> options;

<<<<<<< HEAD
    @Column(name = "POINTS", nullable = false)
    private Float points;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_COMPLETED")
    private Date dateCompleted;
=======
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
        nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "POINTS", nullable = false)
    private Float points;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
        nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "DATE_TIME_COMPLETED")
    private LocalDateTime dateTimeCompleted;
>>>>>>> dev
}
