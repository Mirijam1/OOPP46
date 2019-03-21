package nl.tudelft.gogreen.server.models.embeddable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = "activity")
@ToString(exclude = "activity")
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