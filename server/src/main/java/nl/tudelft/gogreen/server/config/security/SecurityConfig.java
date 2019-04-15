package nl.tudelft.gogreen.server.config.security;

import nl.tudelft.gogreen.server.config.error.AuthFailureHandler;
import nl.tudelft.gogreen.server.config.error.EntryDenied;
import nl.tudelft.gogreen.server.config.error.EntryPoint;
import nl.tudelft.gogreen.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final EntryPoint entryPoint;
    private final EntryDenied entryDenied;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;
    private final TwoFactorAuthenticationDetailsSource twoFactorAuthenticationDetailsSource;
    private final UserRepository userRepository;

    /**
     * instantiates SecurityConfig.
     *
     * @param userDetailsService                   userDetailsService
     * @param dataSource                           dataSource
     * @param passwordEncoder                      passwordEncoder
     * @param entryPoint                           entryPoint
     * @param entryDenied                          entryDenied
     * @param authSuccessHandler                   authSuccessHandler
     * @param authFailureHandler                   authFailureHandler
     * @param twoFactorAuthenticationDetailsSource twoFactorAuthenticationDetailsSource
     */
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          DataSource dataSource, // Ignore auto-inspection here if it complains about missing beans
                          PasswordEncoder passwordEncoder,
                          EntryPoint entryPoint,
                          EntryDenied entryDenied,
                          AuthSuccessHandler authSuccessHandler,
                          AuthFailureHandler authFailureHandler,
                          TwoFactorAuthenticationDetailsSource twoFactorAuthenticationDetailsSource,
                          UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.entryPoint = entryPoint;
        this.entryDenied = entryDenied;
        this.authSuccessHandler = authSuccessHandler;
        this.authFailureHandler = authFailureHandler;
        this.twoFactorAuthenticationDetailsSource = twoFactorAuthenticationDetailsSource;
        this.userRepository = userRepository;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(authProvider());
    }

    /**
     * Creates a new authontication provider.
     * Based on our custom userDetailsService class, and
     * out default password encoder.
     * @return an authentication provider with the specific traits
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        TwoFactorAuthenticationProvider authProvider = new TwoFactorAuthenticationProvider(userRepository);

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(entryDenied)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/2fa/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/profile/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/social/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/restricted/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/status/restricted/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/admin/**").access("hasAnyAuthority('ADMIN_AUTHORITY')")
                .antMatchers("/api/status/admin/**").access("hasAnyAuthority('ADMIN_AUTHORITY')")
                .and()
                .formLogin()
                .authenticationDetailsSource(twoFactorAuthenticationDetailsSource)
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/");

        http.headers().frameOptions().sameOrigin();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/login");
    }
}
