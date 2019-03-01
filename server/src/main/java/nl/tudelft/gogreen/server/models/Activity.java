package nl.tudelft.gogreen.server.models;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;
@Getter
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "ACTIVITY")
public class Activity {
    @Id
    @Column(name = "ACTIVITY_ID", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String activityName;

    @OneToOne
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY_ID")
    private Category category;

    @Column(name = "POINTS", nullable = false)
    private double points;



    //TODO: Add methods
}

