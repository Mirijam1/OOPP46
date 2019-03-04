package nl.tudelft.gogreen.server.tests.controllers;

import nl.tudelft.gogreen.server.service.StatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatusControllerTest {
    @Autowired
    private MockMvc mock;

    @MockBean
    private StatusService statusService;

    @Test
    public void shouldGetOkWhenGettingStatus() throws Exception{
        mock.perform(get("/api/status/test").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("response", is(statusService.getStatus()))
            );
    }

    @Test
    public void shouldGetUnauthorizedWhenGettingRestrictedStatus() throws Exception{
        mock.perform(get("/api/status/restricted/test").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isUnauthorized());
    }

    @WithMockUser(authorities = "USER_AUTHORITY")
    @Test
    public void shouldGetOkWhenGettingRestrictedStatusAsUser() throws Exception{
        mock.perform(get("/api/status/restricted/test").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("response", is(statusService.getRestrictedStatus()))
            );
    }

    @Test
    public void shouldGetUnauthorizedWhenGettingAdminStatus() throws Exception{
        mock.perform(get("/api/status/admin/test").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isUnauthorized());
    }

    @WithMockUser(authorities = "USER_AUTHORITY")
    @Test
    public void shouldGetForbiddenWhenGettingAdminStatusAsUser() throws Exception{
        mock.perform(get("/api/status/admin/test").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"USER_AUTHORITY", "ADMIN_AUTHORITY"})
    @Test
    public void shouldGetOkWhenGettingAdminStatusAsUser() throws Exception{
        mock.perform(get("/api/status/admin/test").contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(
                jsonPath("response", is(statusService.getAdminStatus()))
            );
    }
}
