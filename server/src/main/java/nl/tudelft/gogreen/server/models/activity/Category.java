package nl.tudelft.gogreen.server.models.activity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "CATEGORY")
public class Category {
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<Activity> activities;
}
