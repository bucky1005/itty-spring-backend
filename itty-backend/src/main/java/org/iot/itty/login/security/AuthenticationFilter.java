package org.iot.itty.login.security;

import org.iot.itty.login.service.LoginService;
import org.springframework.core.env.Environment;

public class AuthenticationFilter {
	private final LoginService loginService;
	private final Environment environment;

	public AuthenticationFilter(LoginService loginService, Environment environment) {
		this.loginService = loginService;
		this.environment = environment;
	}


}
