package nl.tudelft.gogreen.server.tests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public class TestUser {
        private String username;
        private @NonNull String password;
    }

    private final String basisEndpoint = "/api/user/";

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    private static String mappedUser;
    private static String mapperUserPasswordOnly;

    @Before
    public void setUp() throws JsonProcessingException {
<<<<<<< HEAD
        mappedUser = mapper.writeValueAsString(new TestUser("tim", "pw"));
        mapperUserPasswordOnly = mapper.writeValueAsString(new TestUser("pw"));
=======
        mappedUser = mapper.writeValueAsString(new TestUser("tim", "password"));
        mapperUserPasswordOnly = mapper.writeValueAsString(new TestUser("password"));
>>>>>>> dev
    }

    // TODO: Maybe make these tests also check for the returned JSON

    @WithAnonymousUser
    @Test
    public void shouldGetUnauthorizedWhenGettingDetails() throws Exception {
        mock.perform(get(basisEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isUnauthorized());
    }

    @Sql(value = {"/data-h2.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnDetailsWhenGettingDetailsAsUser() throws Exception {
        mock.perform(get(basisEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Sql({"/data-h2.sql"})
    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthenticatedWhenLoggingInWithBadCredentials() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", "wrong_password");

        mock.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(mapper.writeValueAsBytes(credentials)))
            .andExpect(status().isUnauthorized());
    }

    @Sql({"/data-h2.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnForbiddenWhenCreatingUserAsUser() throws Exception {
        mock.perform(put(basisEndpoint + "create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mappedUser))
            .andExpect(status().isForbidden());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnOkWhenCreatingUser() throws Exception {
        mock.perform(put(basisEndpoint + "create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mappedUser))
            .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthorizedWhenDeletingAnonymousUser() throws Exception {
        mock.perform(delete(basisEndpoint + "delete").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isUnauthorized());
    }

    @Sql({"/data-h2.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldDeleteAndLogoutUserWhenDeletingAsUser() throws Exception {
        mock.perform(delete(basisEndpoint + "delete").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthorizedWhenUpdatingAnonymousUser() throws Exception {
        mock.perform(patch(basisEndpoint + "update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapperUserPasswordOnly))
            .andExpect(status().isUnauthorized());
    }

    @Sql({"/data-h2.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldUpdateUserWhenUpdatingAsUser() throws Exception {
         mock.perform(patch(basisEndpoint + "update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mappedUser))
            .andExpect(status().isOk());

        //TODO: Add a check to check if the user was actually updated
    }
}
