package org.iot.itty.user.controller;

import org.iot.itty.dto.UserDTO;
import org.iot.itty.user.service.UserService;
import org.iot.itty.user.vo.ResponseUserModify;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<ResponseUserModify> modifyUser(@RequestBody ResponseUserModify modifyUserData){

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(modifyUserData, UserDTO.class);
		UserDTO user = userService.modifyUser(userDTO);

		ResponseUserModify responseUser = new ResponseUserModify();

		if(user.getUserCodePk() == userDTO.getUserCodePk()){
			if(user.getUserNickname().equals(userDTO.getUserNickname()) && user.getUserIntroduction().equals(userDTO.getUserIntroduction())) {
				responseUser.setResultCode(200);
				responseUser.setMessage("프로필 수정 완료 되었습니다.");
			}
		}
		else{
			responseUser.setResultCode(200);
			responseUser.setMessage("비밀번호 확인 바랍니다.");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseUser);
	}
}
