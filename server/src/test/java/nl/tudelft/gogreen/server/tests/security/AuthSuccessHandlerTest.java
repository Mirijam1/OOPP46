package nl.tudelft.gogreen.server.tests.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.server.config.security.AuthSuccessHandler;
import nl.tudelft.gogreen.server.exceptions.handling.ServerError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberMatcher.methodsDeclaredIn;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpServletResponse.class,
    HttpServletRequest.class,
    Authentication.class,
    HttpSessionRequestCache.class,
    SimpleUrlAuthenticationSuccessHandler.class,
    PrintWriter.class})
@PowerMockIgnore({"javax.security.*"})
public class AuthSuccessHandlerTest {
    private static HttpServletResponse response;
    private static HttpServletRequest request;
    private static Authentication authentication;
    private static ObjectMapper mapper;
    private static HttpSessionRequestCache cache;

    @Before
    public void setUp() throws Exception {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        authentication = mock(Authentication.class);
        mapper = mock(ObjectMapper.class);
        cache = mock(HttpSessionRequestCache.class);
        PrintWriter writer = mock(PrintWriter.class);

        // Suppress parent method
        suppress(method(SimpleUrlAuthenticationSuccessHandler.class,
            "clearAuthenticationAttributes"));

        // Suppress all calls to response
        suppress(methodsDeclaredIn(HttpServletResponse.class, PrintWriter.class));

        // Stub
        when(mapper.writeValueAsString(anyString())).thenReturn("");
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void shouldReturnWhenNoCache() throws IOException {
        ArgumentCaptor<ServerError> captor = ArgumentCaptor.forClass(ServerError.class);

        // Return null from cache
        when(cache.getRequest(any(), any())).thenReturn(null);

        // Create handler
        AuthSuccessHandler handler = new AuthSuccessHandler(mapper);

        // Set cache
        handler.injectCache(cache);

        // Call
        handler.onAuthenticationSuccess(request, response, authentication);

        // Verify
        verify(mapper).writeValueAsString(captor.capture());
        assertEquals(captor.getValue().getResponse(), HttpStatus.OK.getReasonPhrase());
    }

    @Test
    public void shouldRemoveWhenUsingDefaultTargetUrl() throws Exception {
        ArgumentCaptor<ServerError> captor = ArgumentCaptor.forClass(ServerError.class);
        SavedRequest savedRequest = mock(SavedRequest.class);

        // Return mock request
        when(cache.getRequest(request, response)).thenReturn(savedRequest);

        // Create handler spy
        AuthSuccessHandler handler = spy(new AuthSuccessHandler(mapper));

        // Set cache
        handler.injectCache(cache);

        // Configure methods of spy
        PowerMockito.doReturn(true).when(handler, "isAlwaysUseDefaultTargetUrl");

        // Suppress remove call
        suppress(method(HttpSessionRequestCache.class, "removeRequest"));

        // Call
        handler.onAuthenticationSuccess(request, response, authentication);

        // Verify
        verify(mapper).writeValueAsString(captor.capture());
        assertEquals(captor.getValue().getResponse(), HttpStatus.OK.getReasonPhrase());
    }

    @Test
    public void shouldRemoveWhenTargetParamIsNotNull() throws Exception {
        ArgumentCaptor<ServerError> captor = ArgumentCaptor.forClass(ServerError.class);
        SavedRequest savedRequest = mock(SavedRequest.class);

        // Return mock request
        when(cache.getRequest(request, response)).thenReturn(savedRequest);

        // Create handler spy
        AuthSuccessHandler handler = spy(new AuthSuccessHandler(mapper));

        // Set cache
        handler.injectCache(cache);

        // Configure request
        when(request.getParameter("string")).thenReturn("another string");

        // Configure methods of spy
        PowerMockito.doReturn(false).when(handler, "isAlwaysUseDefaultTargetUrl");
        PowerMockito.doReturn("string").when(handler, "getTargetUrlParameter");

        // Suppress remove call
        suppress(method(HttpSessionRequestCache.class, "removeRequest"));

        // Call
        handler.onAuthenticationSuccess(request, response, authentication);

        // Verify
        verify(mapper).writeValueAsString(captor.capture());
        assertEquals(captor.getValue().getResponse(), HttpStatus.OK.getReasonPhrase());
    }

    @Test
    public void shouldNotRemoveWhenTargetParamIsNull() throws Exception {
        ArgumentCaptor<ServerError> captor = ArgumentCaptor.forClass(ServerError.class);
        SavedRequest savedRequest = mock(SavedRequest.class);

        // Return mock request
        when(cache.getRequest(request, response)).thenReturn(savedRequest);

        // Create handler spy
        AuthSuccessHandler handler = spy(new AuthSuccessHandler(mapper));

        // Set cache
        handler.injectCache(cache);

        // Configure methods of spy
        PowerMockito.doReturn(false).when(handler, "isAlwaysUseDefaultTargetUrl");
        PowerMockito.doReturn(null).when(handler, "getTargetUrlParameter");

        // Suppress remove call
        suppress(method(HttpSessionRequestCache.class, "removeRequest"));

        // Call
        handler.onAuthenticationSuccess(request, response, authentication);

        // Verify
        verify(mapper).writeValueAsString(captor.capture());
        assertEquals(captor.getValue().getResponse(), HttpStatus.OK.getReasonPhrase());
    }

    @Test
    public void shouldNotRemoveWhenParamHasNoText() throws Exception {
        ArgumentCaptor<ServerError> captor = ArgumentCaptor.forClass(ServerError.class);
        SavedRequest savedRequest = mock(SavedRequest.class);

        // Return mock request
        when(cache.getRequest(request, response)).thenReturn(savedRequest);

        // Create handler spy
        AuthSuccessHandler handler = spy(new AuthSuccessHandler(mapper));

        // Set cache
        handler.injectCache(cache);

        // Configure request
        when(request.getParameter(null)).thenReturn("another string");

        // Configure methods of spy
        PowerMockito.doReturn(false).when(handler, "isAlwaysUseDefaultTargetUrl");
        PowerMockito.doReturn("string").when(handler, "getTargetUrlParameter");

        // Suppress remove call
        suppress(method(HttpSessionRequestCache.class, "removeRequest"));

        // Call
        handler.onAuthenticationSuccess(request, response, authentication);

        // Verify
        verify(mapper).writeValueAsString(captor.capture());
        assertEquals(captor.getValue().getResponse(), HttpStatus.OK.getReasonPhrase());
    }
}
