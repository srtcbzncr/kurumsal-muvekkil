package com.bzncrsrtc.kurumsalmuvekkil.security.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.GenericFilterBean;

import com.bzncrsrtc.kurumsalmuvekkil.responses.ErrorResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckAuthHeaderFilter extends GenericFilterBean {
	
    private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String message = "Bu bölüme erişmek için gerekli yetkilere sahip değilsiniz";
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String authHeader = httpRequest.getHeader("Authorization");
		String acceptLanguageHeader = httpRequest.getHeader("Accept-Language");
		
		if(acceptLanguageHeader != null && acceptLanguageHeader.isEmpty() != true && acceptLanguageHeader.equals("en")) {
			message = "You can not access this resource";
		}
		
		if(authHeader == null || authHeader.isEmpty()) {
			
			ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.name(), message);
			
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            
            ResponseEntity<Object> resp = ResponseHandler.generateResponse(null, HttpStatus.UNAUTHORIZED, errorResponse);
            
            PrintWriter writer = httpResponse.getWriter();
            objectMapper.writeValue(writer, resp.getBody());
		}
		else {
			chain.doFilter(request, response);
		}
	}
}
