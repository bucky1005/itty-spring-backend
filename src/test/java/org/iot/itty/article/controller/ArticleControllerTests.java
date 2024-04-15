package org.iot.itty.article.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.iot.itty.article.aggregate.ArticleEntity;
import org.iot.itty.article.service.ArticleService;
import org.iot.itty.article.vo.RequestModifyFreeBoardArticle;
import org.iot.itty.article.vo.RequestRegistFreeBoardArticle;
import org.iot.itty.article.vo.ResponseArticle;
import org.iot.itty.article.vo.ResponseModifyFreeBoardArticle;
import org.iot.itty.article.vo.ResponseRegistFreeBoardArticle;
import org.iot.itty.article.vo.ResponseSelectAllArticleByUserCodeFk;
import org.iot.itty.config.CustomMapper;
import org.iot.itty.dto.ArticleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleControllerTests {

	@Autowired
	private ArticleController articleController;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@DisplayName("자유게시글 한개 조회 테스트")
	public void selectBulletinArticleByArticleCodePkTest() {

		// given
		int articleCodePk = 1;

		// when
		ResponseEntity<ResponseArticle> response = articleController.selectBulletinArticleByArticleCodePk(articleCodePk);

		// then
		Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
	}

	@Test
	@DisplayName("작성자별 자유게시글 리스트 조회 테스트")
	public void selectAllBulletinArticleByUserCodeFkTest() {

		// given
		int userCodeFk = 1;

		// when
		ResponseEntity<List<ResponseSelectAllArticleByUserCodeFk>> response = articleController.selectAllBulletinArticleByUserCodeFk(userCodeFk);

		// then
		Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
	}

	@Test
	@DisplayName("자유게시판 게시글 등록 테스트")
	public void registBulletinArticleTest() {

		// given
		RequestRegistFreeBoardArticle request = RequestRegistFreeBoardArticle.builder()
			.articleTitle("테스트 제목")
			.articleContent("테스트 내용")
			.userCodeFk(1)
			.build();

		// when
		ResponseEntity<ResponseRegistFreeBoardArticle> response = articleController.registBulletinArticle(request);

		// then
		Assertions.assertThat(HttpStatus.CREATED).isEqualTo(response.getStatusCode());
	}

	@Test
	@DisplayName("자유게시판 게시글 수정 테스트")
	public void modifyBulletinArticleTest() {

		// given
		RequestModifyFreeBoardArticle request = RequestModifyFreeBoardArticle.builder()
			.articleCodePk(1)
			.articleTitle("수정 제목")
			.articleContent("수정 내용")
			.userCodeFk(1)
			.build();

		// when
		ResponseEntity<ResponseModifyFreeBoardArticle> response = articleController.modifyBulletinArticle(request);

		// then
		Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
	}
}
