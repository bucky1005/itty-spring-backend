package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.article.aggregate.LikeEntity;
import org.iot.itty.article.repository.LikeRepository;
import org.iot.itty.dto.LikeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

	private ModelMapper modelMapper;
	private LikeRepository likeRepository;

	@Autowired
	public LikeServiceImpl(ModelMapper modelMapper, LikeRepository likeRepository) {
		this.modelMapper = modelMapper;
		this.likeRepository = likeRepository;
	}

	@Override
	public List<LikeDTO> selectAllLikeByUserCodeFk(int userCodeFk) {
		List<LikeEntity> likeEntityList = likeRepository.findAllByUserCodeFk(userCodeFk);
		return likeEntityList
			.stream()
			.map(LikeEntity -> modelMapper.map(LikeEntity, LikeDTO.class))
			.toList();
	}
}
