package org.iot.itty.article.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.iot.itty.article.service.ArticleService;
import org.iot.itty.article.vo.ResponseArticle;
import org.iot.itty.dto.ArticleDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ArticleController {

	private final ArticleService articleService;
	private final ModelMapper mapper;

	@Autowired
	public ArticleController(ArticleService articleService, ModelMapper mapper) {
		this.articleService = articleService;
		this.mapper = mapper;
	}

	@GetMapping("/article/category/{articleCategory}")
	public ResponseEntity<List<ResponseArticle>> selectAllArticleFromFreeBoard(@PathVariable("articleCategory") int articleCategory) {
		List<ArticleDTO> articleDTOList = articleService.selectAllArticleFromFreeBoard(articleCategory);

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ResponseArticle> responseArticleList = new ArrayList<>();

		if (articleDTOList != null) {
			responseArticleList = articleDTOList
				.stream()
				.map(ArticleDTO -> mapper.map(ArticleDTO, ResponseArticle.class))
				.toList();
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseArticleList);
	}
}
