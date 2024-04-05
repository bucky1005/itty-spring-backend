package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.List;

import org.iot.itty.article.service.LikeService;
import org.iot.itty.article.vo.ResponseSelectAllLikeByUserCodeFk;
import org.iot.itty.dto.LikeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LikeController {

	private final ModelMapper modelMapper;
	private final LikeService likeService;

	@Autowired
	public LikeController(ModelMapper modelMapper, LikeService likeService) {
		this.modelMapper = modelMapper;
		this.likeService = likeService;
	}

	@GetMapping("/user/{userCodeFk}/likes")
	public ResponseEntity<List<ResponseSelectAllLikeByUserCodeFk>> selectAllLikeByUserCodeFk(@PathVariable("userCodeFk") int userCodeFk) {
		List<LikeDTO> likeDTOList = likeService.selectAllLikeByUserCodeFk(userCodeFk);
		List<ResponseSelectAllLikeByUserCodeFk> responseSelectAllLikeByUserCodeFkList = new ArrayList<>();

		if (likeDTOList != null) {
			responseSelectAllLikeByUserCodeFkList = likeDTOList
				.stream()
				.map(LikeDTO -> modelMapper.map(LikeDTO, ResponseSelectAllLikeByUserCodeFk.class))
				.toList();
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseSelectAllLikeByUserCodeFkList);
	}
}
