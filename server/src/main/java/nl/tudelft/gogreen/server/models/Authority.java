package nl.tudelft.gogreen.server.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AID", nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
