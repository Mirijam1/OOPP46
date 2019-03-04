package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "ACTIVITY")
public class Activity {
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String activityName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    private Category category;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id", orphanRemoval = true)
    private Collection<ActivityOption> options;
}

