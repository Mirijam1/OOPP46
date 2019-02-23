package nl.tudelft.gogreen.server.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "BADGE")
public class Badge {
    @Id
    @Column(name = "BADGE_ID", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String badgeName;

    @Column(name = "TYPE", nullable = false)
    private String type;
}
