package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.dto.LikeDTO;

public interface LikeService {
	List<LikeDTO> selectAllLikeByUserCodeFk(int userCodeFk);
}
