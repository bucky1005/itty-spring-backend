package org.iot.itty.login.jwt;

import java.io.IOException;

import org.iot.itty.login.service.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* 요청을 보낸 회원이 헤더에 토큰을 가지고 있는 경우(=로그인된 회원) 동작하는 필터 */
public class JwtFilter extends OncePerRequestFilter {

	private final LoginService loginService;
	private final JwtUtil jwtUtil;

	public JwtFilter(LoginService loginService, JwtUtil jwtUtil) {
		this.loginService = loginService;
		this.jwtUtil = jwtUtil;
	}

	/* UsernamePasswordAuthentication보다 먼저 동작하는 필터 생성 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// 특정 엔드포인트에 대한 요청은 토큰 검증을 하지 않고 처리
		String requestURI = request.getRequestURI();
		if (requestURI.equals("/regist") || requestURI.equals("/login")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		/* 헤더에서 토큰 가져옴 */
		String authorizationHeader = request.getHeader("Authorization");

		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			System.out.println("token: " + token);
			if (jwtUtil.validateToken(token)) {
				Authentication authentication = jwtUtil.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			// 요청 시 헤더에 토큰 안넣어오면 에러뜨게 하는 코드(일단은 주석 처리, 나중에 쓸 것)
			// 	else {
			// 		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
			// 	}
			// } else {
			// 	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다.");
		}
		filterChain.doFilter(request, response);
	}
}
