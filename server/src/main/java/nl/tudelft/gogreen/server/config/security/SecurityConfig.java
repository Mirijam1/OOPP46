package nl.tudelft.gogreen.server.config.security;

import nl.tudelft.gogreen.server.config.error.AuthFailureHandler;
import nl.tudelft.gogreen.server.config.error.EntryDenied;
import nl.tudelft.gogreen.server.config.error.EntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

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

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          DataSource dataSource,
                          PasswordEncoder passwordEncoder,
                          EntryPoint entryPoint,
                          EntryDenied entryDenied,
                          AuthSuccessHandler authSuccessHandler,
                          AuthFailureHandler authFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.entryPoint = entryPoint;
        this.entryDenied = entryDenied;
        this.authSuccessHandler = authSuccessHandler;
        this.authFailureHandler = authFailureHandler;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
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
                .antMatchers("/api/profile/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/restricted/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/status/restricted/**").access("hasAnyAuthority('USER_AUTHORITY')")
                .antMatchers("/api/admin/**").access("hasAnyAuthority('ADMIN_AUTHORITY')")
                .antMatchers("/api/status/admin/**").access("hasAnyAuthority('ADMIN_AUTHORITY')")
                .and()
                .formLogin()
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/login");

        // TODO: Make this dependent on profile
        http.headers().frameOptions().sameOrigin();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/login");
    }
}
