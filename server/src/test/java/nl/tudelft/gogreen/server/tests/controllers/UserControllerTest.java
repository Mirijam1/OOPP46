package nl.tudelft.gogreen.server.tests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("tests")
public class UserControllerTest {
    @Data
    @AllArgsConstructor
    public class TestUser {
        private String username;
        private String password;
        private String mail;
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
        mappedUser = mapper.writeValueAsString(new TestUser("tim", "password", "null@mail"));
        mapperUserPasswordOnly = mapper.writeValueAsString(new TestUser(null, "password", "null2@mail"));
    }

    @WithAnonymousUser
    @Test
    public void shouldGetUnauthorizedWhenGettingDetails() throws Exception {
        mock.perform(get(basisEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnDetailsWhenGettingDetailsAsUser() throws Exception {
        mock.perform(get(basisEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @WithUserDetails(value = "gogreenuser", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldDeleteAndLogoutUserWhenDeletingAsUser() throws Exception {
        mock.perform(delete(basisEndpoint + "delete").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

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
            .andExpect(status().isServiceUnavailable()); // Since email is not available
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnConflictWhenCreatingDuplicate() throws Exception {
        mock.perform(put(basisEndpoint + "create")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(new TestUser("gogreenuser", "password", "null@mail"))))
            .andExpect(status().isConflict());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnBadRequestWhenCreatingWithoutName() throws Exception {
        mock.perform(put(basisEndpoint + "create")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(new TestUser(null, "password", "null@mail"))))
            .andExpect(status().isBadRequest());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnBadRequestWhenCreatingWithoutPassword() throws Exception {
        mock.perform(put(basisEndpoint + "create")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(new TestUser("tim", null, "null@mail"))))
            .andExpect(status().isBadRequest());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnBadRequestWhenCreatingWithBadName() throws Exception {
        mock.perform(put(basisEndpoint + "create")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(new TestUser("t", "password", "null@mail"))))
            .andExpect(status().isBadRequest());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnBadRequestWhenCreatingWithBadPassword() throws Exception {
        mock.perform(put(basisEndpoint + "create")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(new TestUser("tim", "pw", "null@mail"))))
            .andExpect(status().isBadRequest());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthorizedWhenDeletingAnonymousUser() throws Exception {
        mock.perform(delete(basisEndpoint + "delete").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isUnauthorized());
    }

    @WithAnonymousUser
    @Test
    public void shouldReturnUnauthorizedWhenUpdatingAnonymousUser() throws Exception {
        mock.perform(patch(basisEndpoint + "update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapperUserPasswordOnly))
            .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldUpdateUserWhenUpdatingAsUser() throws Exception {
        mock.perform(patch(basisEndpoint + "update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(new TestUser("admin2", "password12345", "not_null@mail"))))
                .andExpect(status().isOk());
    }

    @WithUserDetails(value = "gogreenuser", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldUpdateUserWhenUpdatingAsUserWhenNoChanges() throws Exception {
        mock.perform(patch(basisEndpoint + "update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(new TestUser(null, null, null))))
                .andExpect(status().isOk());
    }

    @Sql({"/test/data-two-users.sql"})
    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailService")
    @Test
    public void shouldReturnConflictWhenUpdatingUserToDuplicateName() throws Exception {
        mock.perform(patch(basisEndpoint + "update")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(new TestUser("test_user", "password", "null@mail"))))
            .andExpect(status().isConflict());
    }
}
