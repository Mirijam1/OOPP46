package nl.tudelft.gogreen.server.config.error;

import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
>>>>>>> dev
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;
=======
>>>>>>> dev

@Component
public final class EntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;

    @Autowired
    public EntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
<<<<<<< HEAD
        Map<String, String> map = new HashMap<>();
        map.put("response", "UNAUTHORIZED");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
=======
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(new ServerError(status.getReasonPhrase())));
>>>>>>> dev
    }
}
