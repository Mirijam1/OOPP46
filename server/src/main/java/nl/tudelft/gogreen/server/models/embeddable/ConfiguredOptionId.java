package nl.tudelft.gogreen.server.models.embeddable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguredOptionId implements Serializable {
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "OPTION", referencedColumnName = "ID")
    private ActivityOption option;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ACTIVITY", referencedColumnName = "ID")
    private CompletedActivity activity;
}