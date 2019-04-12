package nl.tudelft.gogreen.gui.controllers.verification;

public class IntegerAsStringConverter extends InputConverter<String> {
    @Override
    public String convert(String token) throws IllegalArgumentException {
        try {
            Integer.valueOf(token);
            return token;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getErrorMessage() {
        return "Only numbers are allowed!";
    }
}
