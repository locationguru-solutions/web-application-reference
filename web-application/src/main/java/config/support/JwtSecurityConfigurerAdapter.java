package config.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurerAdapter
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
{
	private static final Logger logger = LogManager.getLogger(JwtSecurityConfigurerAdapter.class);

	private final JwtTokenFilter jwtTokenFilter;

	public JwtSecurityConfigurerAdapter(final JwtTokenFilter jwtTokenFilter)
	{
		this.jwtTokenFilter = jwtTokenFilter;
	}

	@Override
	public void configure(final HttpSecurity http)
	{
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
