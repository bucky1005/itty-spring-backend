package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.dto.ArticleDTO;

public interface ArticleService {
	List<ArticleDTO> selectAllArticleFromFreeBoard();

	ArticleDTO selectFreeBoardArticleByArticleCodePk(int articleCodePk);

	ArticleDTO registFreeBoardArticle(ArticleDTO requestArticleDTO);

	ArticleDTO modifyFreeBoardArticle(ArticleDTO requestArticleDTO, int articleCodePk);

	String deleteFreeBoardArticle(int articleCodePk);
}
