package com.saravanank.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.saravanank.authorizationserver.model.User;
import com.saravanank.authorizationserver.service.UserService;

public class CustomAccessTokenClaim implements OAuth2TokenCustomizer<JwtEncodingContext> {

	@Autowired
	private UserService userService;

	@Override
	public void customize(JwtEncodingContext context) {
		// TODO Auto-generated method stub
		User user = userService.findByUsername(context.getPrincipal().getName());
		System.out.println(user);
		context.getClaims().claims(existingClaims -> {
			existingClaims.put("role", user.getRole());
		});
	}
}
