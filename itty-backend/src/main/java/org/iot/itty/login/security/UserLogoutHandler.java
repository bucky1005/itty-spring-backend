package org.iot.itty.login.security;

import java.io.IOException;

import org.iot.itty.login.exception.InvalidToken;
import org.iot.itty.login.jwt.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import io.netty.util.internal.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLogoutHandler implements LogoutHandler, LogoutSuccessHandler {

	private JwtUtil jwtUtil;

	public UserLogoutHandler(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String header = request.getHeader("Authorization");
		System.out.println("Authorization: " + header);

		if (StringUtil.isNullOrEmpty(header) || (jwtUtil.validAccessTokenHeader(request) == null)) {
			throw new InvalidToken("Invalid Token");
		}

		jwtUtil.destroyToken(request);
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		System.out.println("successfully Logout");
	}
}
