package nl.tudelft.gogreen.server.tests.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryControllerTest {
    private final String basicEndpoint = "/api/categories/";

    @Autowired
    private MockMvc mock;

    @Sql({"/data-h2.sql"})
    @Test
    public void shouldReturnCategories() throws Exception {
        mock.perform(get(basicEndpoint)).andExpect(status().isOk());
    }

    @Sql({"/data-h2.sql"})
    @Test
    public void shouldReturnOkWhenRequestingExistingCategory() throws Exception {
        mock.perform(get(basicEndpoint + "Food")).andExpect(status().isOk());
    }

    @Sql({"/data-h2.sql"})
    @Test
    public void shouldReturnNotFoundWhenRequestingNonExistingCategory() throws Exception {
        mock.perform(get(basicEndpoint + "yeet" + UUID.randomUUID())).andExpect(status().isNotFound());
    }
}
