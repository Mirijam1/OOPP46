package nl.tudelft.gogreen.server.models.embeddable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.activity.config.ActivityOption;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionId implements Serializable {
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "OPTION", referencedColumnName = "ID")
    private ActivityOption option;

    @Column(name = "VALUE", nullable = false)
    private String value;
}
