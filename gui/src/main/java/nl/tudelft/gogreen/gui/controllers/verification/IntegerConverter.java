package nl.tudelft.gogreen.gui.controllers.verification;

public class IntegerConverter extends InputConverter<Integer> {
    @Override
    public Integer convert(String token) throws IllegalArgumentException {
        try {
            return Integer.valueOf(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getErrorMessage() {
        return "Only numbers are allowed!";
    }
}
