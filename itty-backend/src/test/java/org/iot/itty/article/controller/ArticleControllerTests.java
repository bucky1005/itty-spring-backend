package org.iot.itty.article.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.iot.itty.article.service.ArticleService;
import org.iot.itty.article.vo.ResponseArticle;
import org.iot.itty.dto.ArticleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleService;

	@MockBean
	private ModelMapper modelMapper;

	@Test
	@DisplayName("자유게시판 전체 조회")
	void selectAllArticleFromFreeBoard() throws Exception {
		// 예제 데이터 준비
		ArticleDTO articleDTO1 = new ArticleDTO(); // ArticleDTO 설정
		articleDTO1.setArticleTitle("Title 1");
		ArticleDTO articleDTO2 = new ArticleDTO(); // ArticleDTO 설정
		articleDTO2.setArticleTitle("Title 2");

		List<ArticleDTO> articleDTOs = List.of(articleDTO1, articleDTO2);

		// ResponseArticle 변환을 위한 예시 데이터
		ResponseArticle responseArticle1 = new ResponseArticle();
		responseArticle1.setArticleTitle(articleDTO1.getArticleTitle());

		ResponseArticle responseArticle2 = new ResponseArticle();
		responseArticle2.setArticleTitle(articleDTO2.getArticleTitle());

		// Service 모킹
		given(articleService.selectAllArticleFromFreeBoard()).willReturn(articleDTOs);


		// ModelMapper 모킹
		given(modelMapper.map(any(ArticleDTO.class), eq(ResponseArticle.class)))
			.willAnswer(invocation -> {
				ArticleDTO source = invocation.getArgument(0);
				ResponseArticle response = new ResponseArticle();
				response.setArticleTitle(source.getArticleTitle());
				// 필요한 다른 필드도 설정
				return response;
			});

		// Service 모킹
		given(articleService.selectAllArticleFromFreeBoard()).willReturn(articleDTOs);

		// 테스트 실행 및 검증
		mockMvc.perform(get("/article/freeboard")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].articleTitle", is("Title 1")))
			.andExpect(jsonPath("$[1].articleTitle", is("Title 2")));
	}
}
