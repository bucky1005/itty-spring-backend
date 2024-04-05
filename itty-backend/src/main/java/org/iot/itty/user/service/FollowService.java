package org.iot.itty.user.service;

import java.util.List;

import org.iot.itty.dto.FollowDTO;

public interface FollowService {
	List<FollowDTO> selectAllFollower(int userCodePk);

	List<FollowDTO> selectAllFollowing(int userCodePk);
}
