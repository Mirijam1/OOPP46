package nl.tudelft.gogreen.server.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.completables.ProgressingAchievement;
import nl.tudelft.gogreen.server.models.completables.AchievedBadge;
import nl.tudelft.gogreen.server.models.social.FriendshipConnection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "USER_PROFILE")
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class UserProfile implements Serializable {
    @JsonIgnore
    @Id
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private UUID id;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class,
            nl.tudelft.gogreen.server.models.JsonView.Detailed.class})
    @OneToOne
    @JoinColumn(name = "USER")
    private User user;

    @JsonView({nl.tudelft.gogreen.server.models.JsonView.Detailed.class,
            nl.tudelft.gogreen.server.models.JsonView.NotDetailed.class})
    @Column(name = "POINTS", nullable = false)
    private Float points;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<CompletedActivity> activities;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<AchievedBadge> badges;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "startUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<FriendshipConnection> friendsAsInitiator;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invitedUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<FriendshipConnection> friendsAsInvitedUser;

    @JsonView(nl.tudelft.gogreen.server.models.JsonView.Detailed.class)
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<ProgressingAchievement> achievements;
}
