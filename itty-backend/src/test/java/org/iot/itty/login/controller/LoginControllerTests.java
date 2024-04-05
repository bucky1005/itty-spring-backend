package org.iot.itty.login.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.service.LoginService;
import org.iot.itty.login.vo.RequestRegist;
import org.iot.itty.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

		// 아이디가 중복되지 않는 경우
		when(userRepository.existsByUserEmail(userDTO.getUserEmail())).thenReturn(false);

		// When
		int userCode = loginService.registUser(userDTO);

		// Then
		assertThat(userCode).isNotEqualTo(0); // userCode가 0이 아닌지 확인
	}

	// @Test
	// @DisplayName("회원 가입 실패 테스트")
	// public void testRegistUser_Failure_UserAlreadyExists() {
	// 	// Given
	// 	UserDTO userDTO = new UserDTO();
	// 	userDTO.setUserEmail("existing@example.com");
	// 	userDTO.setUserPassword("password");
	// 	userDTO.setUserPhoneNumber("1234567890");
	// 	userDTO.setUserNickname("existingUser");
	//
	// 	// 이미 가입된 아이디인 경우
	// 	when(userRepository.existsByUserEmail(userDTO.getUserEmail())).thenReturn(true);
	//
	// 	// When
	// 	IllegalStateException exception =
	// 		org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
	// 			loginServiceImpl.registUser(userDTO);
	// 		});
	//
	// 	// Then
	// 	assertThat(exception.getMessage())
	// 		.isEqualTo(userDTO.getUserEmail() + "'은(는) 이미 가입된 사용자입니다.");
	// }
}
