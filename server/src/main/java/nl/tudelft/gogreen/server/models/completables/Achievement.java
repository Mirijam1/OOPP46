package nl.tudelft.gogreen.server.models.completables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "ACHIEVEMENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Achievement {
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String badgeName;

    @Column(name = "MESSAGE", nullable = false)
    private String achievedMessage;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "REQUIRED_TRIGGERS", nullable = false)
    private Integer requiredTriggers;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "TRIGGER", nullable = false)
    private Trigger trigger;
}
