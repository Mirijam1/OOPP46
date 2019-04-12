package nl.tudelft.gogreen.server.config.security;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class TwoFactorAuthenticationDetails extends WebAuthenticationDetails {
    @Getter
    private String code;

    public TwoFactorAuthenticationDetails(HttpServletRequest request) {
        super(request);

        this.code = request.getParameter("code");
    }
}
