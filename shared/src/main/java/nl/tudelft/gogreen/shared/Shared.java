package nl.tudelft.gogreen.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Shared {
    /**
     * <p>Shared cannot be initialized.</p>
     */
    private Shared() {

    }

    public static String getTestString() {
        return "Hello from shared!";
    }
    public static double roundval(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
