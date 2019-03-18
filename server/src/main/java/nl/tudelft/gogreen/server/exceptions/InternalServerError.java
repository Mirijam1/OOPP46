package nl.tudelft.gogreen.server.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {
}
