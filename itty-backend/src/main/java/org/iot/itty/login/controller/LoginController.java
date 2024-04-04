package org.iot.itty.login.controller;

import org.iot.itty.login.service.LoginService;
import org.iot.itty.login.vo.RequestLogin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	private final LoginService loginService;
	private final ModelMapper modelMapper;

	@Autowired
	public LoginController(LoginService loginService, ModelMapper modelMapper) {
		this.loginService = loginService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/health_check")
	public String healthCheck() {
		return "check check";
	}

	@PostMapping("/regist")
	public ResponseEntity<?> registProcess(RequestLogin requestLogin) {


		return null;
		// return HttpStatus.OK;
	}
}
