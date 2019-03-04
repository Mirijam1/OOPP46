package nl.tudelft.gogreen.shared.tests;

import nl.tudelft.gogreen.shared.Shared;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SharedTest {
    @Test
    public void test() {
        assertEquals(Shared.getTestString(), "Hello from shared!");
    }
}
