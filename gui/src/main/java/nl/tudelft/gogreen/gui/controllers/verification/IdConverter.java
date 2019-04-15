package nl.tudelft.gogreen.gui.controllers.verification;

import java.util.UUID;

public class IdConverter extends InputConverter<UUID> {
    @Override
    public UUID convert(String token) throws IllegalArgumentException {
        return UUID.fromString(token);
    }

    @Override
    public String getErrorMessage() {
        return "Invalid ID";
    }
}
