package com.saravanank.ecommerce.resourceserver.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.saravanank.ecommerce.resourceserver.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

	@Autowired
	private UserService userService;
	
	private final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/**/swagger-ui/**"
    };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(c -> {
			CorsConfigurationSource source = s -> {
				CorsConfiguration cc = new CorsConfiguration();
				cc.setAllowCredentials(true);
				cc.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000"));
				cc.setAllowedHeaders(Arrays.asList("*"));
				cc.setAllowedMethods(Arrays.asList("*"));
				return cc;
			};
			c.configurationSource(source);
		});
		http.csrf().disable()
				.authorizeRequests(auth -> auth.antMatchers(AUTH_WHITELIST).permitAll().antMatchers(HttpMethod.GET, "/**/products").permitAll()
						.antMatchers(HttpMethod.POST, "/**/register").permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()).and().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).accessDeniedHandler(new CustomAccessDeniedHandler()))
				.exceptionHandling(
						exceptions ->
							exceptions.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
								.accessDeniedHandler(new CustomAccessDeniedHandler())
						);
		return http.build();
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	@Autowired
	public void bindAuthenticationBuilder(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userService);
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return JwtDecoders.fromIssuerLocation("http://auth-server:9000");
	}

}
