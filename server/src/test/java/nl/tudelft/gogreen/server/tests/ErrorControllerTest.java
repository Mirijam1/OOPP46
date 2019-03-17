package nl.tudelft.gogreen.server.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerTest {
    @Autowired
    private MockMvc mock;

    @Test
    public void shouldReturnNotFoundWhenGoingToUnknownPage() throws Exception {
        mock.perform(get("/this/better/not/be/an/endpoint/" + UUID.randomUUID() + "/pls/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
