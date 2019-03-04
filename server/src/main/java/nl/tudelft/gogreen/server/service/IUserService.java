package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.user.User;

public interface IUserService {
    User createUser(String username, String password);

    User updateUser(User updatedUser, User user);

    void removeUser(User user);
}
