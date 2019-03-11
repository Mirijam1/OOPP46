package nl.tudelft.gogreen.server.tests.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActivityControllerTest {
    private final String basicEndpoint = "/api/activities/";

    @Autowired
    private MockMvc mock;

    @Test
    public void shouldReturnNotFoundWhenRetrievingNonExistingActivity() throws Exception {
        mock.perform(get(basicEndpoint + "1").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound());
    }
}
