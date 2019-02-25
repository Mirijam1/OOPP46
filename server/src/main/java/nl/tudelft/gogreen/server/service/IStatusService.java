package nl.tudelft.gogreen.server.service;

public interface IStatusService {
    String getStatus();

    String getRestrictedStatus();

    String getAdminStatus();
}
