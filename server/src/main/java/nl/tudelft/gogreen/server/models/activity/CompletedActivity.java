package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "COMPLETED_ACTIVITY")
public class CompletedActivity {
    @Id
    @Column(name = "ID", unique = true, updatable = false, nullable = false)
    private UUID id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "USER", referencedColumnName = "PROFILE_ID")
    private UserProfile profile;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private Activity activity;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.activity", orphanRemoval = true)
    private Collection<ConfiguredOption> options;

    @Column(name = "POINTS", nullable = false)
    private Float points;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_COMPLETED")
    private Date dateCompleted;
}
