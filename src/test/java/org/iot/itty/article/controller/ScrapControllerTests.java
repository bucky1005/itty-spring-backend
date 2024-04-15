package org.iot.itty.article.controller;


import java.util.List;

import org.assertj.core.api.Assertions;
import org.iot.itty.article.service.ScrapService;
import org.iot.itty.article.vo.ResponseSelectAllScrapByUserCodeFk;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ScrapControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ScrapController scrapController;

	@Autowired
	private ScrapService scrapService;

	@Test
	@DisplayName("회원별 스크렙 게시글 리스트 출력 테스트")
	public void testSelectAllScrapByUserCodeFk() {

		// given
		int userCodeFK = 1;

		// when
		ResponseEntity<List<ResponseSelectAllScrapByUserCodeFk>> response =
			scrapController.selectAllScrapByUserCodeFk(userCodeFK);

		// then
		Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
	}

	@Test
	@DisplayName("게시글 스크랩 추가 테스트")
	public void testRegistScrap() {

		// given

		// when

		// then
	}
}
