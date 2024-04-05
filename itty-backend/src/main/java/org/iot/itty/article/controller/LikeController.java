package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.List;

import org.iot.itty.article.service.LikeService;
import org.iot.itty.article.vo.ResponseSelectAllReplyLikedByUserCodeFk;
import org.iot.itty.dto.ReplyDTO;
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
	public ResponseEntity<List<ResponseSelectAllReplyLikedByUserCodeFk>> selectAllLikeByUserCodeFk(@PathVariable("userCodeFk") int userCodeFk) {
		List<ReplyDTO> replyDTOList = likeService.selectAllLikeByUserCodeFk(userCodeFk);
		List<ResponseSelectAllReplyLikedByUserCodeFk> responseSelectAllReplyLikedByUserCodeFkList = new ArrayList<>();

		if (replyDTOList != null) {
			responseSelectAllReplyLikedByUserCodeFkList = replyDTOList
				.stream()
				.map(ReplyDTO -> modelMapper.map(ReplyDTO, ResponseSelectAllReplyLikedByUserCodeFk.class))
				.toList();
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseSelectAllReplyLikedByUserCodeFkList);
	}
}
