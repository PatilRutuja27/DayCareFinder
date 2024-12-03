package tda.darkarmy.daycare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;


	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception
	{
		http
				.csrf()
				.disable()
				.cors()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/users/login", "/users/signup", "/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.logout()
				.logoutUrl("/api/v1/auth/logout")
				.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

		return http.build();
	}
}
