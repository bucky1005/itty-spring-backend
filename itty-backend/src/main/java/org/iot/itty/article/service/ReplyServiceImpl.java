package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.article.aggregate.ReplyEntity;
import org.iot.itty.article.repository.ReplyRepository;
import org.iot.itty.dto.ReplyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService{

	private final ReplyRepository replyRepository;
	private final ModelMapper mapper;

	@Autowired
	public ReplyServiceImpl(ReplyRepository replyRepository, ModelMapper mapper) {
		this.replyRepository = replyRepository;
		this.mapper = mapper;
	}

	@Override
	public List<ReplyDTO> selectReplyByArticleCodeFk(int articleCodePk) {
		List<ReplyEntity> replyEntityList = replyRepository.findAllByArticleCodeFk(articleCodePk);

		return replyEntityList
			.stream()
			.map(ReplyEntity -> mapper.map(ReplyEntity, ReplyDTO.class))
			.toList();
	}
}
