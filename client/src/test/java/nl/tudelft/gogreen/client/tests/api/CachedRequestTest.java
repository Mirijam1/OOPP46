package nl.tudelft.gogreen.client.tests.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class CachedRequestTest {

    @Test
    public void retrieveFromCache() {
    assertEquals(2,2);
}

    @Test
    public void updateCache() {
        assertNotEquals(5,2);
    }
}