package org.iot.itty.login.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.service.LoginService;
import org.iot.itty.login.vo.RequestRegist;
import org.iot.itty.login.vo.ResponseRegist;
import org.iot.itty.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private LoginService loginService;

	@Test
	@DisplayName("회원 가입 성공 테스트")
	public void testRegistUser_Success() {
		// 준비(DB에 존재하지 않는 값으로 입력)
		RequestRegist requestRegist = new RequestRegist();
		requestRegist.setUserEmail("test001@example.com");
		requestRegist.setUserPassword("password");
		requestRegist.setUserName("testName");
		requestRegist.setUserNickname("nickName");
		requestRegist.setUserPhoneNumber("123456789");

		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail(requestRegist.getUserEmail());
		userDTO.setUserPassword(requestRegist.getUserPassword());
		userDTO.setUserName(requestRegist.getUserName());
		userDTO.setUserNickname(requestRegist.getUserNickname());
		userDTO.setUserPhoneNumber(requestRegist.getUserPhoneNumber());

		// When
		ResponseRegist result = loginService.registUser(userDTO);

		// Then
		Assertions.assertThat(HttpStatus.CREATED).isEqualTo(result);
	}
}
