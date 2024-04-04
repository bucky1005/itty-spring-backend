package org.iot.itty.user.service;

import org.apache.catalina.User;
import org.iot.itty.dto.UserDTO;
import org.iot.itty.user.aggregate.UserEntity;
import org.iot.itty.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

	private final ModelMapper modelMapper;
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}

	@Transactional
	@Override
	public UserDTO modifyUser(UserDTO userDTO) {
		UserEntity user = userRepository.findById(userDTO.getUserCodePk()).orElseThrow(IllegalAccessError::new);
		user.setUserNickname(userDTO.getUserNickname());
		user.setUserIntroduction(userDTO.getUserIntroduction());

		return modelMapper.map(user, UserDTO.class);
	}
}
