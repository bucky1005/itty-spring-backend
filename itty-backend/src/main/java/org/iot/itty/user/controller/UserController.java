package org.iot.itty.user.controller;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.user.service.UserService;
import org.iot.itty.user.vo.ResponseModifyUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
	private final ModelMapper modelMapper;
	private final UserService userService;

	@Autowired
	public UserController(ModelMapper modelMapper, UserService userService) {
		this.modelMapper = modelMapper;
		this.userService = userService;
	}

	@PostMapping("/user/modify")
	public ResponseEntity<ResponseModifyUser> modifyUser(@RequestBody ResponseModifyUser modifyUserData){

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(modifyUserData, UserDTO.class);
		UserDTO user = userService.modifyUser(userDTO);

	}
}
