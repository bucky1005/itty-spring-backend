package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.List;

import org.iot.itty.article.service.ArticleService;
import org.iot.itty.article.service.ReplyService;
import org.iot.itty.article.vo.RequestModifyFreeBoardArticle;
import org.iot.itty.article.vo.RequestRegistFreeBoardArticle;
import org.iot.itty.article.vo.ResponseArticle;
import org.iot.itty.article.vo.ResponseDeleteFreeBoardArticle;
import org.iot.itty.article.vo.ResponseModifyFreeBoardArticle;
import org.iot.itty.article.vo.ResponseRegistFreeBoardArticle;
import org.iot.itty.article.vo.ResponseSelectAllArticleByUserCodeFk;
import org.iot.itty.dto.ArticleDTO;
import org.iot.itty.dto.ReplyDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@GetMapping("/article/freeboard")
	public ResponseEntity<List<ResponseArticle>> selectAllArticleFromFreeBoard() {
		List<ArticleDTO> articleDTOList = articleService.selectAllArticleFromFreeBoard();

		// mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
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

	/* 게시글코드(article_code_pk) 로 자유게시판 게시글 한개 조회 */
	@GetMapping("/article/freeboard/{articleCodePk}")
	public ResponseEntity<ResponseArticle> selectFreeBoardArticleByArticleCodePk(@PathVariable("articleCodePk") int articleCodePk) {
		ArticleDTO articleDTO = articleService.selectFreeBoardArticleByArticleCodePk(articleCodePk);
		articleDTO.setReplyDTOList(replyService.selectReplyByArticleCodeFk(articleCodePk));

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return ResponseEntity.status(HttpStatus.OK).body(mapper.map(articleDTO, ResponseArticle.class));

	}

	/* 회원코드(user_code_fk) 로 회원별 작성된 게시글 조회 */
	@GetMapping("/user/{userCodeFk}/articles")
	public ResponseEntity<List<ResponseSelectAllArticleByUserCodeFk>> selectAllArticleByUserCodeFk(@PathVariable("userCodeFk") int userCodeFk) {
		List<ArticleDTO> articleDTOList = articleService.selectAllArticleByUserCodeFk(userCodeFk);
		List<ResponseSelectAllArticleByUserCodeFk> responseSelectAllArticleByUserCodeFkList = new ArrayList<>();

		if (articleDTOList != null) {
			responseSelectAllArticleByUserCodeFkList = articleDTOList
				.stream()
				.peek(articleDTO -> articleDTO.setReplyDTOList(replyService.selectReplyByArticleCodeFk(articleDTO.getArticleCodePk())))
				.map(ArticleDTO -> mapper.map(ArticleDTO, ResponseSelectAllArticleByUserCodeFk.class))
				.toList();
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseSelectAllArticleByUserCodeFkList);
	}

	/* 자유게시판 게시글 등록 */
	@PostMapping("/article/freeboard/regist")
	public ResponseEntity<ResponseRegistFreeBoardArticle> registFreeBoardArticle(@RequestBody RequestRegistFreeBoardArticle requestRegistFreeBoardArticle) {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ArticleDTO requestArticleDTO = mapper.map(requestRegistFreeBoardArticle, ArticleDTO.class);
		ArticleDTO responseArticleDTO = articleService.registFreeBoardArticle(requestArticleDTO);

		responseArticleDTO.setReplyDTOList(new ArrayList<>());

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(mapper.map(responseArticleDTO, ResponseRegistFreeBoardArticle.class));
	}

	/* 자유게시판 게시글 수정 */
	@PutMapping("/article/freeboard/{articleCodePk}/modify")
	public ResponseEntity<ResponseModifyFreeBoardArticle> modifyFreeBoardArticle(
		@RequestBody RequestModifyFreeBoardArticle requestModifyFreeBoardArticle,
		@PathVariable("articleCodePk") int articleCodePk
	)
	{
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ArticleDTO requestArticleDTO = mapper.map(requestModifyFreeBoardArticle, ArticleDTO.class);
		ArticleDTO responseArticleDTO = articleService.modifyFreeBoardArticle(requestArticleDTO, articleCodePk);

		responseArticleDTO.setReplyDTOList(replyService.selectReplyByArticleCodeFk(articleCodePk));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(mapper.map(responseArticleDTO, ResponseModifyFreeBoardArticle.class));
	}

	/* 자유게시판 게시글 삭제 */
	@DeleteMapping("/article/freeboard/{articleCodePk}/delete")
	public ResponseEntity<ResponseDeleteFreeBoardArticle> deleteFreeBoardArticle(
		@PathVariable("articleCodePk") int articleCodePk
	)
	{
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		String returnedMessage = articleService.deleteFreeBoardArticle(articleCodePk);

		ResponseDeleteFreeBoardArticle responseDeleteFreeBoardArticle = new ResponseDeleteFreeBoardArticle();
		responseDeleteFreeBoardArticle.setMessage(returnedMessage);

		return ResponseEntity.status(HttpStatus.OK).body(responseDeleteFreeBoardArticle);
	}
}
