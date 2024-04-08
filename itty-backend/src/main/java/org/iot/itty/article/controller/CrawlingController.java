package org.iot.itty.article.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.iot.itty.article.aggregate.CrawledArticle;
import org.iot.itty.article.service.CrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CrawlingController {

	private final CrawlingService crawlingService;

	@Autowired
	public CrawlingController(CrawlingService crawlingService) {
		this.crawlingService = crawlingService;
	}

	@GetMapping("/trends")
	public ResponseEntity<List<CrawledArticle>> getTrendNews() throws IOException {
		List<CrawledArticle> articleList = crawlingService.getTrendNews();

		return ResponseEntity.status(HttpStatus.OK).body(articleList);
	}
}
