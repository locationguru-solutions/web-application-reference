package config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.locationguru.csf.service.*;
import config.support.JwtSecurityConfigurerAdapter;
import config.support.JwtTokenFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration
{
	private static final Logger logger = LogManager.getLogger(SecurityConfiguration.class);

	private final UserService userService;
	private final TokenService tokenService;
	private final AuthenticationService authenticationService;
	private final JsonMapper jsonMapper;

	public SecurityConfiguration(final UserService userService, final TokenService tokenService, final AuthenticationService authenticationService, final JsonMapper jsonMapper)
	{
		this.userService = userService;
		this.tokenService = tokenService;
		this.authenticationService = authenticationService;
		this.jsonMapper = jsonMapper;
	}

	@Bean
	public SecurityFilterChain configure(final HttpSecurity http) throws Exception
	{
		// Configuring stateless session management
		return http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				   // Disabling CSRF protection due to stateless authentication
				   .and().csrf().disable()

				   .cors().and()

				   // Configuring authentication rules and exceptions
				   .authorizeHttpRequests()
				   .requestMatchers(HttpMethod.POST, "/api/authentications/login", "/authentications/login").permitAll() // Allowing only POST requests for user login
				   .requestMatchers("/static/**").permitAll() // Allowing static resources
				   .requestMatchers("/monitoring/**").permitAll() // Allowing static resources
				   .requestMatchers("/api/**", "/**").authenticated() // Allowing API endpoints to be authenticated
				   .anyRequest().permitAll() // Allow all other requests

				   // Configuring adapter for JWT based authentication
				   .and().apply(new JwtSecurityConfigurerAdapter(new JwtTokenFilter(this.userService, this.tokenService, this.authenticationService, this.jsonMapper)))
				   .and().build();
	}
}
