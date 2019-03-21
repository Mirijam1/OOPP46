package nl.tudelft.gogreen.server.tests;

import nl.tudelft.gogreen.server.exceptions.handling.ErrorController;
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpServletRequest.class})
public class ErrorControllerTest {
    private HttpServletRequest mockRequest;
    private ErrorController errorController;

    @Before
    public void setUp() {
        errorController = new ErrorController();
        mockRequest = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void shouldReturnNotFoundWhenPageNotFound() {
        when(mockRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn("404");

        assertEquals(errorController.error(mockRequest), new ServerError(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @Test
    public void shouldReturnForbiddenWhenForbidden() {
        when(mockRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn("403");

        assertEquals(errorController.error(mockRequest), new ServerError(HttpStatus.FORBIDDEN.getReasonPhrase()));
    }

    @Test
    public void shouldReturnCodeWhenErrorNotFound() {
        when(mockRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn("424242");

        assertEquals(errorController.error(mockRequest), new ServerError("424242"));
    }

    @Test
    public void shouldReturnErrorPath() {
        assertEquals(errorController.getErrorPath(), "/error");
    }
}
