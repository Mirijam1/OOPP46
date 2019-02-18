package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.User;

public interface IUserService {
    User createUser(User user);

    User updateUser(User user);

    void removeUser(User user);
}
