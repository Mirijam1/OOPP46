package nl.tudelft.gogreen.server.models.activity.config;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.activity.Activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACTIVITY_OPTION")
public class ActivityOption {
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "INPUT_TYPE", nullable = false)
    private InputType inputType;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionId.option", orphanRemoval = true)
    private Collection<OptionValue> options;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private Activity activity;
}
