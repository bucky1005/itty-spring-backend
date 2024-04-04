package org.iot.itty.user.service;

import org.iot.itty.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	private final ModelMapper modelMapper;
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}
}
