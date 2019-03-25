package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;
import nl.tudelft.gogreen.server.models.completables.Trigger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude = "category")
@ToString(exclude = "category")
public class Activity {
    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Integer id;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "NAME", nullable = false, unique = true)
    private String activityName;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    private Category category;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<ActivityOption> options;

    @JsonIgnore
    @ElementCollection(targetClass = Trigger.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "TRIGGER")
    @JoinTable(name = "TRIGGERS", joinColumns = @JoinColumn(name = "ID"))
    private Collection<Trigger> triggers;
}

