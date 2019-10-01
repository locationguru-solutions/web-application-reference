package config;

import com.locationguru.csf.service.*;
import config.support.JwtSecurityConfigurerAdapter;
import config.support.JwtTokenFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
		extends WebSecurityConfigurerAdapter
{
	private static final Logger logger = LogManager.getLogger(SecurityConfiguration.class);

	private final UserService userService;
	private final TokenService tokenService;
	private final AuthenticationService authenticationService;

	public SecurityConfiguration(final UserService userService, final TokenService tokenService, final AuthenticationService authenticationService)
	{
		this.userService = userService;
		this.tokenService = tokenService;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		// Configuring stateless session management
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// Disabling CSRF protection due to stateless authentication
			.and().csrf().disable()

			.cors().and()

			// Configuring authentication rules and exceptions
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/authentications/login").permitAll() // Allowing only POST requests for user login
			.antMatchers("/static/**").permitAll() // Allowing static resources
			.antMatchers("/**").authenticated() // Allowing API endpoints to be authenticated
			.anyRequest().permitAll() // Allow all other requests

			// Configuring adapter for JWT based authentication
			.and().apply(new JwtSecurityConfigurerAdapter(new JwtTokenFilter(userService, tokenService, authenticationService)));
	}
}
