package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.server.models.user.User;

public interface IUserService {
    User createUser(String username, String password, String mail);

    User updateUser(User updatedUser, User user);

    void removeUser(User user);

    void generateToken(User user);

    BasicResponse verifyUser(User user, Integer token);

    void completeVerification(User user);
}
