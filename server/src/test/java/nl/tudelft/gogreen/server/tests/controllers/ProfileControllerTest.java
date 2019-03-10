package nl.tudelft.gogreen.server.tests.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProfileControllerTest {
<<<<<<< HEAD
    private final String basicEndpoint = "/api/restricted/profile/";
=======
    private final String basicEndpoint = "/api/profile/";
>>>>>>> dev

    @Autowired
    private MockMvc mock;

    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthorizedWhenRetrievingProfile() throws Exception {
        mock.perform(get(basicEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isUnauthorized());
    }

    @Sql({"/data-h2.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnOkWhenRetrievingProfileAsUser() throws Exception {
        mock.perform(get(basicEndpoint)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
    }
}
