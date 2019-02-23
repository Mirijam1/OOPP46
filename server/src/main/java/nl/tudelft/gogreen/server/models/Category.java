package nl.tudelft.gogreen.server.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CATEGORY")
public class Category {
    @Id
    @Column(name = "CATEGORY_ID", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
