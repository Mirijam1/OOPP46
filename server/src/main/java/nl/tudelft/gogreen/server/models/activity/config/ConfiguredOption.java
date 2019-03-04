package nl.tudelft.gogreen.server.models.activity.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.server.models.embeddable.ConfiguredOptionId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CONFIGURED_OPTION")
public class ConfiguredOption {
    @EmbeddedId
    private ConfiguredOptionId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "INPUT_TYPE", nullable = false)
    private InputType inputType;

    @Column(name = "VALUE", nullable = false, updatable = false)
    private String value;
}
