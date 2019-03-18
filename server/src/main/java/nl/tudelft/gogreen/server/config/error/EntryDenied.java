package nl.tudelft.gogreen.server.config.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class EntryDenied implements AccessDeniedHandler {
    private final ObjectMapper mapper;

    @Autowired
    public EntryDenied(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        HttpStatus status = HttpStatus.FORBIDDEN;

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(new ServerError(status.getReasonPhrase())));
    }
}
