package nl.tudelft.gogreen.coapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpringApplication.class})
public class DumbMainTest {
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SpringApplication.class);
    }

    @Test
    public void shouldStartSpringWhenRunningJar() {
        CarbonServer.main("");

        PowerMockito.verifyStatic(SpringApplication.class, times(1));
        SpringApplication.run(CarbonServer.class);
    }
}
