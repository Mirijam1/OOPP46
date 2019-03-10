package nl.tudelft.gogreen.server.exceptions.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final String errorPath = "/error";

    @RequestMapping(errorPath)
    public ServerError error(HttpServletRequest request) {
        int status = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) + "");

        for (HttpStatus httpStatus : HttpStatus.values()) {
            if (status == httpStatus.value()) {
                return new ServerError(httpStatus.getReasonPhrase());
            }
        }

        return new ServerError(status + "");
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }
}
