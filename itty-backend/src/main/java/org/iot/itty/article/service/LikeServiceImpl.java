package org.iot.itty.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iot.itty.article.aggregate.LikeEntity;
import org.iot.itty.article.aggregate.ReplyEntity;
import org.iot.itty.article.repository.LikeRepository;
import org.iot.itty.article.repository.ReplyRepository;
import org.iot.itty.dto.ReplyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

	private ModelMapper modelMapper;
	private LikeRepository likeRepository;
	private ReplyRepository replyRepository;

	@Autowired
	public LikeServiceImpl(ModelMapper modelMapper, LikeRepository likeRepository, ReplyRepository replyRepository) {
		this.modelMapper = modelMapper;
		this.likeRepository = likeRepository;
		this.replyRepository = replyRepository;
	}

	@Override
	public List<ReplyDTO> selectAllLikeByUserCodeFk(int userCodeFk) {
		List<LikeEntity> likeEntityList = likeRepository.findAllByUserCodeFk(userCodeFk);
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		for (LikeEntity likeEntity: likeEntityList) {
			ReplyEntity replyEntity = replyRepository.findById(likeEntity.getReplyCodeFk()).orElseThrow(IllegalAccessError::new);
			replyDTOList.add(modelMapper.map(replyEntity, ReplyDTO.class));
		}
		return replyDTOList;
	}
}
