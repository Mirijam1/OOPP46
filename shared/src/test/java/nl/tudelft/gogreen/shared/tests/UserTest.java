package nl.tudelft.gogreen.shared.tests;

import nl.tudelft.gogreen.shared.models.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void shouldCreateCorrectUserObject() {
        User user = new User("name", "password");

        assertEquals(user.getName(), "name");
        assertEquals(user.getPassword(), "password");
    }
}
