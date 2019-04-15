package nl.tudelft.gogreen.server.tests.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class ActivityControllerTest {
    private final String basicEndpoint = "/api/activities/";

    @Autowired
    private MockMvc mock;

    @Test
    public void shouldReturnNotFoundWhenRetrievingNonExistingActivity() throws Exception {
        mock.perform(get(basicEndpoint + "-1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnRequestedVegetarianMealActivity() throws Exception {
        mock.perform(get(basicEndpoint + "0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(0)))
                .andExpect(jsonPath("activityName", is("Vegetarian meal")));
    }

    @Test
    public void shouldReturnOkWhenRequestingFromCategory() throws Exception {
        mock.perform(get(basicEndpoint + "category/Transport").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenRetrievingNonExistingOptions() throws Exception {
        mock.perform(get(basicEndpoint + "-1/options").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOptionsWhenRequestingOptions() throws Exception {
        mock.perform(get(basicEndpoint + "0/options").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
