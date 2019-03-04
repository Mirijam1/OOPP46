package nl.tudelft.gogreen.server.tests.models;

import nl.tudelft.gogreen.server.models.Authority;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorityTest {
    private static Authority authority;

    @BeforeClass
    public static void setUp() {
        authority = new Authority(0L, "NAME");
    }

    @Test
    public void shouldReturnName() {
        assertEquals(authority.getAuthority(), "NAME");
    }
}
