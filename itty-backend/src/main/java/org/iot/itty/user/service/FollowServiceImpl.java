package org.iot.itty.user.service;

import java.util.List;

import org.iot.itty.dto.FollowDTO;
import org.iot.itty.user.aggregate.FollowEntity;
import org.iot.itty.user.repository.FollowRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService{

	private ModelMapper modelMapper;
	private FollowRepository followRepository;

	@Autowired
	public FollowServiceImpl(ModelMapper modelMapper, FollowRepository followRepository) {
		this.modelMapper = modelMapper;
		this.followRepository = followRepository;
	}

	@Override
	public List<FollowDTO> selectAllFollower(int userCodePk) {
		List<FollowEntity> followerEntityList = followRepository.findAllByFollowerCodeFk(userCodePk);

		return followerEntityList
			.stream()
			.map(FollowEntity -> modelMapper.map(FollowEntity, FollowDTO.class))
			.toList();
	}

	@Override
	public List<FollowDTO> selectAllFollowing(int userCodePk) {
		List<FollowEntity> followingEntityList = followRepository.findAllByFolloweeCodeFk(userCodePk);

		return followingEntityList
			.stream()
			.map(FollowEntity -> modelMapper.map(FollowEntity, FollowDTO.class))
			.toList();
	}
}
