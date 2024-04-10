package org.iot.itty.login.security;

import java.util.Date;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;

public class AccessToken {

	private final LoginService loginService;
	private final Environment environment;

	public AccessToken(LoginService loginService, Environment environment) {
		this.loginService = loginService;
		this.environment = environment;
	}

	public String createAccessToken(Claims claims, Authentication authResult,
		HttpServletResponse response, UserDTO userDetails) {

		/* 이메일을 조회하여 해당 유저의 사용자의 인증(principal) 이름을 반환 */
		UserDTO userDTO = loginService.searchUserEmail(authResult.getName());

		String accessToken;
		accessToken = Jwts.builder().setClaims(claims)
			.setSubject(authResult.getName())
			.claim("userCodePk", userDTO.getUserCodePk())
			.claim("userEmail", userDTO.getUserEmail())
			.setExpiration(new Date(System.currentTimeMillis() +
				Long.parseLong(environment.getProperty("token.expiration_time"))))
			.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
			.compact();

		return accessToken;
	}
}
