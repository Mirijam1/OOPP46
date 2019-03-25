package nl.tudelft.gogreen.server.models.completables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "BADGE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Badge {
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String badgeName;

    @Column(name = "MESSAGE", nullable = false)
    private String achievedMessage;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "TRIGGER", nullable = false)
    private Trigger trigger;

    @JsonIgnore
    @ElementCollection(targetClass = Trigger.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "TRIGGER")
    @JoinTable(name = "TRIGGERS_COMPLETE", joinColumns = @JoinColumn(name = "ID"))
    private Collection<Trigger> triggers;
}
