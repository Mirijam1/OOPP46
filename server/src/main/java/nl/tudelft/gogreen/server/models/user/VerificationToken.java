package nl.tudelft.gogreen.server.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "VERIFICATION_TOKEN", indexes = {
        @Index(name = "TOKEN_INDEX", columnList = "TOKEN")
    })
public class VerificationToken {
    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "TOKEN", unique = true, nullable = false, updatable = false)
    private Integer token;

    @Column(name = "EXPIRES", nullable = false, updatable = false)
    private Long expiresAt;

    @OneToOne
    @JoinColumn(name = "USER", referencedColumnName = "USER_ID")
    private User user;
}
