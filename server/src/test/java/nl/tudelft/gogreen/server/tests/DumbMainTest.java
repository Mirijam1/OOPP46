package nl.tudelft.gogreen.server.tests;

import com.mashape.unirest.http.Unirest;
import nl.tudelft.gogreen.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringApplication.class, Unirest.class})
public class DumbMainTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SpringApplication.class);
        PowerMockito.suppress(PowerMockito.everythingDeclaredIn(Unirest.class));
    }

    @Test
    public void shouldStartSpringWhenRunningJar() {
        Server.main("");

        PowerMockito.verifyStatic(SpringApplication.class, times(1));
        SpringApplication.run(Server.class);
    }
}
