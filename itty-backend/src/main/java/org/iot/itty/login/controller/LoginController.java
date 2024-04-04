package org.iot.itty.login.controller;

import org.iot.itty.login.service.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;

public class LoginController {
	private final LoginService loginService;
	private final ModelMapper modelMapper;

	public LoginController(LoginService loginService, ModelMapper modelMapper) {
		this.loginService = loginService;
		this.modelMapper = modelMapper;
	}

	@PostMapping("/regist")
	public String regist() {
		return null;
	}
}
