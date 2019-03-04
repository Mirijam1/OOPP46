package nl.tudelft.gogreen.server.models.activity.config;

import lombok.Data;
import nl.tudelft.gogreen.server.models.embeddable.OptionId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "OPTION_VALUE")
public class OptionValue {
    @EmbeddedId
    private OptionId optionId;

    @Column(name = "INDEX")
    private Integer index;
}
