package com.saravanank.ecommerce.resourceserver.config;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.saravanank.ecommerce.resourceserver.model.RestResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger logger = Logger.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String errorMessage = e.getMessage();
		RestResponse res = new RestResponse();
		res.setStatus(status.value());
		res.setError("Unauthenticated");
		res.setMessage(errorMessage);
		res.setTimestamp((new Date()).toString());
		String json = ow.writeValueAsString(res);
		logger.error("Unauthenticated access 401 " + e.getMessage());
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(json);
	}

}
