package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.dto.ArticleDTO;

public interface ArticleService {
	List<ArticleDTO> selectAllArticleFromFreeBoard();

	ArticleDTO registFreeBoardArticle(ArticleDTO requestArticleDTO);
}
