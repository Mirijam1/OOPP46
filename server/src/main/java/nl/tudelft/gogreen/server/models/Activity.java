package nl.tudelft.gogreen.server.models;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "ACTIVITY_TABLE")
public class Activity {
    @Id
    @Column(name = "UID", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String activityname;

    @Column(name = "CATEGORY", nullable = false)
    private Category category;

    @Column(name = "POINTS", nullable = false)
    private double points;

    public Activity(String id, String name, Category Category, double Points) {
        super();
        this.id = id;
        this.activityname = name;
        this.category = Category;
        this.points=Points;
    }


    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIgnore
//    @OrderBy
//    @JoinTable(
//        name = "ACTIVITY_DETAILS",
//        joinColumns = @JoinColumn(
//            name = "CATEGORY_ID",
//            referencedColumnName = "CID"
//        )
//          )
    private Collection<Activity> activities;
/*
TODO: ADD METHODS
@Override
public boolean isAccountNonExpired() {
return !this.isExpired();
}

@Override
public boolean isAccountNonLocked() {
return !this.isLocked();
}

/*
Maybe implement this later, for now the credentials never expire
* /
@Override
public boolean isCredentialsNonExpired() {
return true;
}
*/

}

