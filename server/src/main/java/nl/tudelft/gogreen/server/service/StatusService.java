package nl.tudelft.gogreen.server.service;

import org.springframework.stereotype.Service;

@Service
public class StatusService implements IStatusService {
    private static final String DEFAULT_STATUS_RESPONSE = "If you receive this the API is online";
    private static final String DEFAULT_RESTRICTED_STATUS_RESPONSE = "If you receive this you have been authenticated as user";
    private static final String DEFAULT_ADMIN_STATUS_RESPONSE = "If you receive this you have been authenticated as admin";

    @Override
    public String getStatus() {
        return DEFAULT_STATUS_RESPONSE;
    }

    @Override
    public String getRestrictedStatus() {
        return DEFAULT_RESTRICTED_STATUS_RESPONSE;
    }

    @Override
    public String getAdminStatus() {
        return DEFAULT_ADMIN_STATUS_RESPONSE;
    }
}
