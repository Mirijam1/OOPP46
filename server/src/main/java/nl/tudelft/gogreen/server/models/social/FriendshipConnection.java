package nl.tudelft.gogreen.server.models.social;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.social.Friendship;
import nl.tudelft.gogreen.shared.models.social.SocialUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "FRIENDSHIP_CONNECTION")
@EqualsAndHashCode(exclude = {"startUser", "invitedUser"})
@ToString(exclude = {"startUser", "invitedUser"})
public class FriendshipConnection {
    @JsonIgnore
    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private UUID id;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "EXT_ID", nullable = false, unique = true, updatable = false)
    private UUID externalId;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @JoinColumn(name = "USER_INIT", referencedColumnName = "ID", nullable = false)
    @JsonBackReference
    @ManyToOne
    private UserProfile startUser;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @JoinColumn(name = "USER_INVITED", referencedColumnName = "ID", nullable = false)
    @JsonBackReference
    @ManyToOne
    private UserProfile invitedUser;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "ACCEPTED", nullable = false)
    private Boolean accepted;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "DATE_TIME_INVITED")
    private LocalDateTime invitedAt;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @Column(name = "DATE_TIME_ACCEPTED")
    private LocalDateTime acceptedAt;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "DATE_TIME_STARTED")
    private LocalDateTime friendShipStartedAt;

    /**
     * <p>Returns a {@link Friendship} instance.</p>
     *
     * @param initiator Indicates whether to add the initiator or the invited to the shared mode
     * @return A {@link Friendship} instance.
     */
    public Friendship toSharedModel(boolean initiator) {
        return Friendship.builder()
                .accepted(accepted)
                .acceptedAt(acceptedAt)
                .externalId(externalId)
                .friendShipStarted(friendShipStartedAt)
                .invited(invitedAt)
                .friend(SocialUser.builder()
                        .username(initiator ? startUser.getUser().getUsername() : invitedUser.getUser().getUsername())
                        .build())
                .build();
    }
}
