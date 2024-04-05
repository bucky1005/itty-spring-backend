package org.iot.itty.user.controller;

import java.util.List;

import org.iot.itty.dto.FollowDTO;
import org.iot.itty.dto.UserDTO;
import org.iot.itty.user.service.FollowService;
import org.iot.itty.user.service.UserService;
import org.iot.itty.user.vo.ResponseFollow;
import org.iot.itty.user.vo.ResponseFollower;
import org.iot.itty.user.vo.ResponseFollowing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FollowController {

	private final ModelMapper modelMapper;
	private final UserService userService;
	private final FollowService followService;

	@Autowired
	public FollowController(ModelMapper modelMapper, UserService userService, FollowService followService) {
		this.modelMapper = modelMapper;
		this.userService = userService;
		this.followService = followService;
	}

	@GetMapping("user/{userCodePk}/follower")
	public ResponseEntity<List<ResponseFollower>> selectAllFollower(@PathVariable("userCodePk") int userCodePk) {
		List<FollowDTO> followerDTOList = followService.selectAllFollower(userCodePk);
		List<UserDTO> userDTOList = userService.selectAllFollowerUser(followerDTOList);

		return ResponseEntity.status(HttpStatus.OK).body(userDTOList
			.stream()
			.map(UserDTO -> modelMapper.map(UserDTO, ResponseFollower.class))
			.toList());
	}

	@GetMapping("user/{userCodePk}/following")
	public ResponseEntity<List<ResponseFollowing>> selectAllFollowing(@PathVariable("userCodePk") int userCodePk) {
		List<FollowDTO> followingDTOList = followService.selectAllFollowing(userCodePk);
		List<UserDTO> userDTOList = userService.selectAllFollowingUser(followingDTOList);

		return ResponseEntity.status(HttpStatus.OK).body(userDTOList
			.stream()
			.map(UserDTO -> modelMapper.map(UserDTO, ResponseFollowing.class))
			.toList());
	}

	@PostMapping("/following/{userCodePk}/{followeeCodeFk}")
	public ResponseEntity<ResponseFollow> addFollowing(
		@PathVariable("userCodePk") int userCodePk,
		@PathVariable("followeeCodeFk") int followeeCodeFk
	)
	{
		FollowDTO responseFollowDTO = followService.addFollowing(userCodePk, followeeCodeFk);

		if (responseFollowDTO == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(responseFollowDTO, ResponseFollow.class));
	}
}
