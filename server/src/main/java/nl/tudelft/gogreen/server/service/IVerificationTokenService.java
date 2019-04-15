package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.VerificationToken;
import org.springframework.http.HttpStatus;

public interface IVerificationTokenService {
    VerificationToken createTokenForUser(User user, Integer token);

    HttpStatus verifyToken(Integer token, User user);
}
