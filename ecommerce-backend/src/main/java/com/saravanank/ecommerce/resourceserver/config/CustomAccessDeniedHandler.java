package com.saravanank.ecommerce.resourceserver.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.saravanank.ecommerce.resourceserver.model.RestResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private static final Logger logger = Logger.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpStatus status = HttpStatus.FORBIDDEN;
	    String errorMessage = accessDeniedException.getMessage();
		RestResponse res = new RestResponse();
		res.setStatus(status.value());
		res.setError("Access denied");
		res.setMessage(errorMessage);
		logger.error("Access denied 403 " + accessDeniedException.getMessage());
		res.setTimestamp((new Date()).toString());
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(res);
		response.setStatus(status.value());
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    response.getWriter().write(json);
	}

}
