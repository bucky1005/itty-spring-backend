package org.iot.itty.article.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iot.itty.article.service.TrendArticleService;
import org.iot.itty.article.vo.ResponseTrendArticle;
import org.iot.itty.dto.TrendArticleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TrendArticleController {

	private final ModelMapper modelMapper;
	private final TrendArticleService trendArticleService;

	@Autowired
	public TrendArticleController(ModelMapper modelMapper, TrendArticleService trendArticleService) {
		this.modelMapper = modelMapper;
		this.trendArticleService = trendArticleService;
	}

	@GetMapping("/article/trend")
	public ResponseEntity<List<ResponseTrendArticle>> selectAllTrendArticle() {
		List<TrendArticleDTO> trendArticleDTOList = trendArticleService.selectAllTrendArticle();

		return ResponseEntity.status(HttpStatus.OK).body(trendArticleDTOList
			.stream()
			.map(TrendArticleDTO -> modelMapper.map(TrendArticleDTO, ResponseTrendArticle.class))
			.toList()
		);
	}

	@GetMapping("/article/trend/add")
	public ResponseEntity<Map<String, String>> addTrendArticle() throws IOException, ParseException {
		List<TrendArticleDTO> trendArticleDTOList = trendArticleService.addTrendArticle();

		// System.out.println(trendArticleDTOList);
		Map<String, String> result = new HashMap<>();
		if (trendArticleDTOList != null) {
			result.put("message", trendArticleDTOList.size() + " trend article(s) added successfully.");
		} else {
			result.put("message", "Failed to add trend articles.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
}
