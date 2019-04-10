package nl.tudelft.gogreen.server.config.security;

import nl.tudelft.gogreen.server.exceptions.UnauthorizedException;
import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.repository.UserRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String code = ((TwoFactorAuthenticationDetails) authentication.getDetails()).getCode();
        User user = userRepository.findUserByUsername(authentication.getName());

        if (user == null) {
            throw new BadCredentialsException("Invalid user");
        }

        if (user.isTfaEnabled()) {
            Totp totp = new Totp(user.getTfaSecret());

            if (!checkLong(code) || !totp.verify(code)) {
                throw new BadCredentialsException("Invalid 2FA code");
            }
        }

        Authentication result = super.authenticate(authentication);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    private boolean checkLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException exception) {
            return false;
        }

        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    protected void doAfterPropertiesSet() {
        // I have no clue why this fixed the problem, but after a quick search on google it appears I'm not the only
        // one with this bug. When you start the server without this dummy logic it will crash, because this class needs
        // an instance of a userdetails service. With this logic not present this is not the case, but when there
        // is something is this function it magically works again.
        // Dropping down to java 7 also fixes it, but that isn't really an option.
        // Thanks stackoverflow, I guess
        if (super.getUserDetailsService() != null) {
            System.out.println("UserDetailsService has been configured properly");
        }
    }
}
