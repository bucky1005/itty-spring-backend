package org.iot.itty.login.service;

import org.iot.itty.login.vo.ResponseLogin;
import org.iot.itty.user.aggregate.UserEntity;
import org.iot.itty.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

	public final UserRepository userRepository;
	public final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public LoginServiceImpl(UserRepository userRepository,
		BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void registProcess(ResponseLogin responseLogin) {
		String userEmail = responseLogin.getUserEmail();
		String userPassword = responseLogin.getUserPassword();

		Boolean isExist = userRepository.existsBy(userEmail);

		if(!isExist){
			return ;
		}

		UserEntity data = new UserEntity();

		data.setUserEmail(userEmail);
		data.setUserPassword(bCryptPasswordEncoder.encode(userPassword));
		data.setUserRole("ROLE_USER");
	}
}
