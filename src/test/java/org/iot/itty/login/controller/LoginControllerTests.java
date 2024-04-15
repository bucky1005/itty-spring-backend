package org.iot.itty.login.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.service.LoginService;
import org.iot.itty.login.service.LoginServiceImpl;
import org.iot.itty.login.vo.RequestRegist;
import org.iot.itty.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional	// MockBean을 사용하지 않고 실제 DB로 테스트 할 때 테스트 내용이 반영되지 않도록 함
@AutoConfigureMockMvc
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginService loginService;

	@Autowired
	private LoginServiceImpl loginServiceImpl;

	@Test
	@DisplayName("회원 가입 성공 테스트")
	public void testRegistUser_Success() {
		// given(DB에 존재하지 않는 값으로 입력)
		RequestRegist requestRegist = new RequestRegist();
		requestRegist.setUserEmail("newUserTest@example.com");
		requestRegist.setUserPassword("test");
		requestRegist.setUserName("testname");
		requestRegist.setUserNickname("test");
		requestRegist.setUserPhoneNumber("010-1234-1234");

		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail(requestRegist.getUserEmail());
		userDTO.setUserPassword(requestRegist.getUserPassword());
		userDTO.setUserName(requestRegist.getUserName());
		userDTO.setUserNickname(requestRegist.getUserNickname());
		userDTO.setUserPhoneNumber(requestRegist.getUserPhoneNumber());

		// When
		int userCode = loginService.registUser(userDTO);
		System.out.println("userCode: " + userCode);

		// Then
		assertThat(userCode).isNotEqualTo(null);
	}

	@Test
	@DisplayName("회원 가입 실패 테스트")
	public void registUserTest_fail() {
		// given(DB에 존재하는 값으로 입력)
		RequestRegist requestRegist = new RequestRegist();
		requestRegist.setUserEmail("test021@example.com");
		requestRegist.setUserPassword("test");
		requestRegist.setUserName("testnamete");
		requestRegist.setUserNickname("test");
		requestRegist.setUserPhoneNumber("010-1234-1234");

		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail(requestRegist.getUserEmail());
		userDTO.setUserPassword(requestRegist.getUserPassword());
		userDTO.setUserName(requestRegist.getUserName());
		userDTO.setUserNickname(requestRegist.getUserNickname());
		userDTO.setUserPhoneNumber(requestRegist.getUserPhoneNumber());

		// when
		boolean existUser = userRepository.existsByUserEmail(userDTO.getUserEmail());

		//then
		assertThat(existUser).isTrue();
	}

	@Test
	@DisplayName("회원 탈퇴 성공 테스트")
	public void	withdrawalUserTest_Success() {

		//given
		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail("test019@example.com");
		userDTO.setUserNickname("test");
		userDTO.setUserPassword("test");
		userDTO.setUserName("testname");
		userDTO.setUserPhoneNumber("010-1234-1234");

		//when
		boolean result = loginService.withdrawalUser(userDTO);

		//then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("회원 탈퇴 실패 테스트")
	public void withdrawalUserMethodFailTest() {

		//given(DB에서 이미 탈퇴한 회원 정보를 세팅)
		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail("test018@example.com");
		userDTO.setUserNickname("test");
		userDTO.setUserPassword("test");
		userDTO.setUserName("testname");
		userDTO.setUserPhoneNumber("010-1234-1234");

		//when
		boolean result = loginService.withdrawalUser(userDTO);

		//then
		assertThat(result).isFalse();
	}
}
