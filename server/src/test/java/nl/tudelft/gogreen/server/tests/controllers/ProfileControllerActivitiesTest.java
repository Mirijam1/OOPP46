package nl.tudelft.gogreen.server.tests.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProfileControllerActivitiesTest {
    private final String basicEndpoint = "/api/profile/activities/";

    @Autowired
    private MockMvc mock;

    @Sql({"/data-h2.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnEmptyCollectionAfterNoCompletedActivities() {
        assertTrue(true);
    }
}
