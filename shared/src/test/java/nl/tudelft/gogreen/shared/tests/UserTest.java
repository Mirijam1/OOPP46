package nl.tudelft.gogreen.shared.tests;

import nl.tudelft.gogreen.shared.models.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void shouldCreateCorrectUserObject() {
        User user = new User("name", "password", "mail@example.com");

        assertEquals(user.getUsername(), "name");
        assertEquals(user.getPassword(), "password");
        assertEquals(user.getMail(), "mail@example.com");
    }
}
