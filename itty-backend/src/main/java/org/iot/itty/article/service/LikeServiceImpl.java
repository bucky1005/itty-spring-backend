package org.iot.itty.article.service;

import java.util.ArrayList;
import java.util.List;

import org.iot.itty.article.aggregate.ArticleEntity;
import org.iot.itty.article.aggregate.ArticleLikeEntity;
import org.iot.itty.article.aggregate.ReplyLikeEntity;
import org.iot.itty.article.aggregate.ReplyEntity;
import org.iot.itty.article.repository.ArticleLikeRepository;
import org.iot.itty.article.repository.ArticleRepository;
import org.iot.itty.article.repository.ReplyLikeRepository;
import org.iot.itty.article.repository.ReplyRepository;
import org.iot.itty.dto.ArticleDTO;
import org.iot.itty.dto.ReplyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

	private ModelMapper modelMapper;
	private ReplyLikeRepository replyLikeRepository;
	private ReplyRepository replyRepository;
	private ArticleLikeRepository articleLikeRepository;
	private ArticleRepository articleRepository;

	@Autowired
	public LikeServiceImpl(
		ModelMapper modelMapper,
		ReplyLikeRepository replyLikeRepository,
		ReplyRepository replyRepository,
		ArticleLikeRepository articleLikeRepository,
		ArticleRepository articleRepository
	)
	{
		this.modelMapper = modelMapper;
		this.replyLikeRepository = replyLikeRepository;
		this.replyRepository = replyRepository;
		this.articleLikeRepository = articleLikeRepository;
		this.articleRepository = articleRepository;
	}

	@Override
	public List<ReplyDTO> selectAllLikeByUserCodeFk(int userCodeFk) {
		List<ReplyLikeEntity> replyLikeEntityList = replyLikeRepository.findAllByUserCodeFk(userCodeFk);
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		for (ReplyLikeEntity replyLikeEntity : replyLikeEntityList) {
			ReplyEntity replyEntity = replyRepository.findById(replyLikeEntity.getReplyCodeFk()).orElseThrow(IllegalAccessError::new);
			replyDTOList.add(modelMapper.map(replyEntity, ReplyDTO.class));
		}
		return replyDTOList;
	}

	@Override
	public List<ArticleDTO> selectAllArticleLikedbyUserCodeFk(int userCodeFk) {
		List<ArticleLikeEntity> articleLikeEntityList = articleLikeRepository.findAllByUserCodeFk(userCodeFk);
		List<ArticleDTO> articleDTOList = new ArrayList<>();
		for (ArticleLikeEntity articleLikeEntity : articleLikeEntityList) {
			ArticleEntity articleEntity = articleRepository.findById(articleLikeEntity.getArticleCodeFk()).orElseThrow(IllegalAccessError::new);
			articleDTOList.add(modelMapper.map(articleEntity, ArticleDTO.class));
		}
		return articleDTOList;
	}
}
