package nl.tudelft.gogreen.server.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

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

