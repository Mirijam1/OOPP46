package nl.tudelft.gogreen.server.service;

import javax.mail.MessagingException;

public interface IMailService {
    boolean mailAvailable();

    void checkConnection();

    void sendMessage(String target, String subject, String message) throws MessagingException;

    void sendRegistrationMessage(String target, String name, String code, String externalId);

    void sendRegistrationCompleteMessage(String target, String name);
}
