package org.iot.itty.login.security;

import java.io.IOException;
import java.util.ArrayList;

import org.iot.itty.login.service.LoginService;
import org.iot.itty.login.vo.RequestLogin;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter {//extends UsernamePasswordAuthenticationFilter {
	private final LoginService loginService;
	private final Environment environment;

	public AuthenticationFilter(LoginService loginService, Environment environment) {
		this.loginService = loginService;
		this.environment = environment;
	}

	// @Override
	// public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
	// 	AuthenticationException {
	// 	return super.attemptAuthentication(request, response);

		// try {
		// 	RequestLogin requestLogin =
		// 		new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
		//
		// 	return getAuthenticationManager().authenticate(
		// 		new UsernamePasswordAuthenticationToken(
		// 			requestLogin.getUserEmail(), requestLogin.getUserPassword(), new ArrayList<>())
		// 	);
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }
	// }
}
