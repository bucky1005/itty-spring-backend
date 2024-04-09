package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iot.itty.article.service.LikeService;
import org.iot.itty.article.vo.ResponseSelectAllArticleLikedByUserCodeFk;
import org.iot.itty.article.vo.ResponseSelectAllReplyLikedByUserCodeFk;
import org.iot.itty.dto.ArticleDTO;
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

	/* 회원이 좋아요 누른 게시글 리스트, 댓글 리스트 조회 */
	@GetMapping("/user/{userCodeFk}/likes")
	public ResponseEntity<Map<String, Object>> selectAllLikeByUserCodeFk(@PathVariable("userCodeFk") int userCodeFk) {

		/* 해당 회원이 좋아요를 누른 댓글 리스트 가져오기 */
		List<ReplyDTO> replyDTOList = likeService.selectAllLikeByUserCodeFk(userCodeFk);

		/* 해당 회원이 좋아요를 누른 게시글 리스트 가져오기*/
		List<ArticleDTO> articleDTOList = likeService.selectAllArticleLikedbyUserCodeFk(userCodeFk);
		List<ResponseSelectAllReplyLikedByUserCodeFk> responseSelectAllReplyLikedByUserCodeFkList = new ArrayList<>();
		List<ResponseSelectAllArticleLikedByUserCodeFk> responseSelectAllArticleLikedByUserCodeFkList = new ArrayList<>();

		Map<String, Object> result = new HashMap<>();

		responseSelectAllReplyLikedByUserCodeFkList = replyDTOList
			.stream()
			.map(ReplyDTO -> modelMapper.map(ReplyDTO, ResponseSelectAllReplyLikedByUserCodeFk.class)).toList();

		responseSelectAllArticleLikedByUserCodeFkList = articleDTOList
			.stream()
			.map(ArticleDTO -> modelMapper.map(ArticleDTO, ResponseSelectAllArticleLikedByUserCodeFk.class)).toList();

		result.put("userCode", userCodeFk);
		result.put("likedArticleList", responseSelectAllArticleLikedByUserCodeFkList);
		result.put("likedReplyList", responseSelectAllReplyLikedByUserCodeFkList);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
