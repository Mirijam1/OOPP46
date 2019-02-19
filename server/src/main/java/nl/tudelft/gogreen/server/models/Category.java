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
@Table(name = "CATEGORY_TABLE")
public class Category {
    @Id
    @Column(name = "CID", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String categoryname;

    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    private String description;

}
