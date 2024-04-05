package org.iot.itty.login.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iot.itty.login.vo.RequestLogin;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final LoginService loginService;
	private final Environment environment;

	public AuthenticationFilter(AuthenticationManager authenticationManager,
								LoginService loginService, Environment environment) {
		super(authenticationManager);
		this.loginService = loginService;
		this.environment = environment;
	}

	/* 사용자 인증 시도 시 동작 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		try {
			RequestLogin requestLogin =
				new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

			/* 사용자가 전달한 id, pwd를 사용해 authentication 토큰 생성 */
			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestLogin.getUserEmail(), requestLogin.getUserPassword(), new ArrayList<>())
			);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* 사용자 인증 성공(로그인 시 입력한 정보가 DB와 일치) 시 토큰 생성 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult) throws IOException, ServletException {

		String userName = ((User)authResult.getPrincipal()).getUsername();	// 로그인한 유저의 아이디(이메일) 저장
		log.info("userName: " + userName);

		UserDTO userDetails = loginService.getUserDetailsByUserEmail(userName);

		/* 회원 권한 리스트 */
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");

		Claims claims = Jwts.claims().setSubject(userDetails.getUserEmail());
		// claims.put("auth", roles);
		claims.put("auth", roles.stream().filter(role -> role.equals("ROLE_USER")).collect(Collectors.toList()));

		/* 이메일을 조회하여 해당 유저의 사용자의 인증(principal) 이름을 반환 */
		UserDTO userDTO = loginService.searchUserEmail(authResult.getName());

		/* 토큰 생성 */
		String token = Jwts.builder().setClaims(claims)
			.setSubject(authResult.getName())
			.claim("userCodePk", userDTO.getUserCodePk())
			.claim("userEmail", userDTO.getUserEmail())
				.setExpiration(new Date(System.currentTimeMillis() +
					Long.parseLong(environment.getProperty("token.expiration_time"))))
					.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
						.compact();
		response.addHeader("token", token);
		response.addHeader("userCodePk", String.valueOf(userDetails.getUserCodePk()));
		response.addHeader("userEmail", userDetails.getUserEmail());
	}
}
