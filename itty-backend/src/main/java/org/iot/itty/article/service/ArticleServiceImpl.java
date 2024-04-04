package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.article.aggregate.ArticleEntity;
import org.iot.itty.article.repository.ArticleRepository;
import org.iot.itty.dto.ArticleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;
	private final ModelMapper mapper;

	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper mapper) {
		this.articleRepository = articleRepository;
		this.mapper = mapper;
	}

	@Override
	public List<ArticleDTO> selectAllArticleFromFreeBoard(int articleCategory) {
		List<ArticleEntity> articleEntityList = articleRepository.findAllByArticleCategory(articleCategory);

		return articleEntityList
			.stream()
			.map(ArticleEntity -> mapper.map(ArticleEntity, ArticleDTO.class))
			.toList();
	}
}
