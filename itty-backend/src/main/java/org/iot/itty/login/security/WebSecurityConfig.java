package org.iot.itty.login.security;

import org.iot.itty.login.jwt.JwtFilter;
import org.iot.itty.login.jwt.JwtUtil;
import org.iot.itty.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final LoginService loginService;
	private final Environment environment;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtUtil jwtUtil;

	@Autowired
	public WebSecurityConfig(LoginService loginService, Environment environment,
		BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
		this.loginService = loginService;
		this.environment = environment;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder =
			http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(loginService).passwordEncoder(bCryptPasswordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		// csrf disable
		/* jwt 토큰을 사용하면 세션을 stateless 상태로 관리하기 때문에 csrf를 disable 상태로 설정한다. */
		http.csrf((auth) -> auth.disable());

		// http basic 인증 방식 disable
		/* jwt 토큰 사용 시 http basic 인증 불필요 */
		http.httpBasic((auth) -> auth.disable());

		// 인가(Authorization)
		http.authorizeHttpRequests((auth) -> auth
			.requestMatchers("/login", "/", "regist").permitAll()

			// 전체 권한 설정(추후 수정)
			.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/test")).hasRole("USER")

			// 권한 부여 설정을 하지 않은 요청은 로그인된 사용자에게만 허용
			.anyRequest().authenticated())
			.authenticationManager(authenticationManager)

			// 세션 설정
			/* jwt 방식에서는 무상태성에 의해 세션을 항상 STATELESS 상태로 관리 */
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilter(getAuthenticationFilter(authenticationManager));
		http.addFilterBefore(new JwtFilter(loginService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
		// 받아온 매개변수 http를 build 타입으로 반환
		return http.build();
	}

	private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
		return new AuthenticationFilter(authenticationManager, loginService, environment);
	}


}
