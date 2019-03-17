package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String activityName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    private Category category;

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

