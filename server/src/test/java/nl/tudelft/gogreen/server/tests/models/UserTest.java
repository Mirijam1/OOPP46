package nl.tudelft.gogreen.server.tests.models;

import nl.tudelft.gogreen.server.models.Authority;
import nl.tudelft.gogreen.server.models.user.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private static User user;
    private static User lockedUser;

    @BeforeClass
    public static void setUp() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(0L, "USER_AUTHORITY"));

        user = User.builder()
            .username("admin")
            .password("password")
            .authorities(authorities)
            .id(UUID.randomUUID())
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();

        lockedUser = User.builder()
            .username("admin_locked")
            .password("password")
            .authorities(authorities)
            .id(UUID.randomUUID())
            .enabled(false)
            .expired(true)
            .locked(true)
            .build();
    }

    @Test
    public void shouldNotBeLockedWhenActive() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void shouldNotBeExpiredWhenNotExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void credentialsShouldNeverBeDisabled() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void shouldBeLockedWhenLocked() {
        assertFalse(lockedUser.isAccountNonLocked());
    }

    @Test
    public void shouldBeExpiredWhenExpired() {
        assertFalse(lockedUser.isAccountNonExpired());
    }
}
