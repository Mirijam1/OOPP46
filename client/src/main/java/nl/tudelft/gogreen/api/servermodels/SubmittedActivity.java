package nl.tudelft.gogreen.api.servermodels;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubmittedActivity {
    private Integer id;
    private String activityName;
    private String description;
    private Options[] options;
    private AchievedBadge[] achievedBadges;

}
