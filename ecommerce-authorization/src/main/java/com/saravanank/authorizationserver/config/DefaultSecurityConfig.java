package com.saravanank.authorizationserver.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.saravanank.authorizationserver.service.CustomAuthProvider;

@EnableWebSecurity
public class DefaultSecurityConfig {

	@Autowired
	private CustomAuthProvider customAuthProvider;

	@Bean
	SecurityFilterChain defaultSecurity(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin(Customizer.withDefaults()).logout()
				.logoutSuccessUrl("http://127.0.0.1:3000?logout=true").invalidateHttpSession(true)
				.deleteCookies("JSESSIONID");
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
		return http.build();
	}

	@Autowired
	public void bindAuthProvider(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(customAuthProvider);
	}

}
