package org.iot.itty.login.service;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.user.aggregate.UserEntity;
import org.iot.itty.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	public final UserRepository userRepository;
	public final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public LoginServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public int registUser(UserDTO userDTO) {
		String userEmail = userDTO.getUserEmail();
		String userPassword = userDTO.getUserPassword();
		String userPhoneNumber = userDTO.getUserPhoneNumber();
		String userNickname = userDTO.getUserNickname();

		// 아이디 중복 체크
		Boolean isExist = userRepository.existsByUserEmail(userEmail);

		if (isExist) {
			throw new IllegalStateException("'" + userEmail + "'는(은) 이미 존재하는 사용자 입니다.");
		}

		UserEntity data = new UserEntity();

		data.setUserEmail(userEmail);
		data.setUserPassword(bCryptPasswordEncoder.encode(userPassword));
		data.setUserPhoneNumber(userPhoneNumber);
		data.setUserNickname(userNickname);
		data.setUserRole("ROLE_USER");
		data.setUserDeleteStatus(0);
		data.setUserIntroduction("내 소개가 아직 없습니다.");

		userRepository.save(data);
		return data.getUserCodePk();
	}
}