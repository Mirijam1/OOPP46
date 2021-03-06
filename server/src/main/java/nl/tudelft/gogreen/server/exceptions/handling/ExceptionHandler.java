package nl.tudelft.gogreen.server.exceptions.handling;

import nl.tudelft.gogreen.server.exceptions.BadRequestException;
import nl.tudelft.gogreen.server.exceptions.ConflictException;
import nl.tudelft.gogreen.server.exceptions.ForbiddenException;
import nl.tudelft.gogreen.server.exceptions.InternalServerError;
import nl.tudelft.gogreen.server.exceptions.NotFoundException;
import nl.tudelft.gogreen.server.exceptions.ServiceUnavailableException;
import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    /**
     * Handling Unauthorized Requests.
     * @return ServerError
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ServerError> handleUnauthorized() {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(), null), status);
    }
    /**
     * Handling Forbidden Exceptions.
     * @return ServerError
     */

    @org.springframework.web.bind.annotation.ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ServerError> handleForbidden() {
        HttpStatus status = HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(), null), status);
    }
    /**
     * Handling NotFound Exceptions.
     * @return ServerError
     */

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ServerError> handleNotFound() {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(), null), status);
    }

    /**
     * Handle bad requests.
     * @return ServerError
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ServerError> handleBadRequest() {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(), null), status);
    }

    /**
     * Handle conflicts exception.
     * @return ServerError
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ConflictException.class)
    public ResponseEntity<ServerError> handleConflict(ConflictException conflictException) {
        HttpStatus status = HttpStatus.CONFLICT;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(), conflictException.getMessage()), status);
    }

    /**
     * Handle Errors.
     * @return ServerError
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ServerError> handleError() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(), null), status);
    }

    /**
     * Handle ServiceUnavailableException.
     * @return ServerError
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ServerError> handleServiceUnavailable(
            ServiceUnavailableException serviceUnavailableException) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;

        return new ResponseEntity<>(new ServerError(status.getReasonPhrase(),
                serviceUnavailableException.getMessage()), status);
    }
}
