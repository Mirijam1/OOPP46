package nl.tudelft.gogreen.gui.controllers.verification;

public abstract class InputConverter<K> {
    public abstract K convert(String token) throws IllegalArgumentException;

    public abstract String getErrorMessage();
}