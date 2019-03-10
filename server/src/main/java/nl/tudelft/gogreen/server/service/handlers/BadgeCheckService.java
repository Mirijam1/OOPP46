package nl.tudelft.gogreen.server.service.handlers;

import nl.tudelft.gogreen.server.models.activity.CompletedActivity;
import nl.tudelft.gogreen.server.models.user.UserProfile;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import org.springframework.stereotype.Service;

@Service
public class BadgeCheckService implements IBadgeCheckService {
    @Override
    public void checkBadge(CompletedActivity completedActivity,
                           UserProfile userProfile,
                           SubmitResponse submitResponse) {
        //TODO: Implement
    }
}
