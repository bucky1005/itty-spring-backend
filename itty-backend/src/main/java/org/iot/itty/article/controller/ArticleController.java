package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.List;

import org.iot.itty.article.service.ArticleService;
import org.iot.itty.article.service.ReplyService;
import org.iot.itty.article.vo.RequestRegistFreeBoardArticle;
import org.iot.itty.article.vo.ResponseArticle;
import org.iot.itty.article.vo.ResponseRegistFreeBoardArticle;
import org.iot.itty.dto.ArticleDTO;
import org.iot.itty.dto.ReplyDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ArticleController {

	private final ArticleService articleService;
	private final ReplyService replyService;
	private final ModelMapper mapper;

	@Autowired
	public ArticleController(ArticleService articleService, ReplyService replyService, ModelMapper mapper) {
		this.articleService = articleService;
		this.replyService = replyService;
		this.mapper = mapper;
	}

	/* 자유게시판 전체조회 */
	@GetMapping("/article/category/2")
	public ResponseEntity<List<ResponseArticle>> selectAllArticleFromFreeBoard() {
		List<ArticleDTO> articleDTOList = articleService.selectAllArticleFromFreeBoard();

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ResponseArticle> responseArticleList = new ArrayList<>();

		if (articleDTOList != null) {
			responseArticleList = articleDTOList
				.stream()
				.peek(articleDTO -> articleDTO.setReplyDTOList(replyService.selectReplyByArticleCodeFk(articleDTO.getArticleCodePk())))
				.map(ArticleDTO -> mapper.map(ArticleDTO, ResponseArticle.class))
				.toList();
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseArticleList);
	}

	@PostMapping("/article/category/2/regist")
	public ResponseEntity<ResponseRegistFreeBoardArticle> registFreeBoardArticle(@RequestBody RequestRegistFreeBoardArticle requestRegistFreeBoardArticle) {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ArticleDTO requestArticleDTO = mapper.map(requestRegistFreeBoardArticle, ArticleDTO.class);
		ArticleDTO responseArticleDTO = articleService.registFreeBoardArticle(requestArticleDTO);

		List<ReplyDTO> replyDTOList = new ArrayList<>();
		responseArticleDTO.setReplyDTOList(replyDTOList);

		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(responseArticleDTO, ResponseRegistFreeBoardArticle.class));
	}

}
