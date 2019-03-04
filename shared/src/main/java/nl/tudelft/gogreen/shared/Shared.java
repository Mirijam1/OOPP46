package nl.tudelft.gogreen.shared;

public class Shared {
    /**
     * <p>Shared cannot be initialized.</p>
     */
    private Shared() {

    }

    public static String getTestString() {
        return "Hello from shared!";
    }
}
