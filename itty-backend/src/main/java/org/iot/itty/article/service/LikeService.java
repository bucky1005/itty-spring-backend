package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.dto.LikeDTO;
import org.iot.itty.dto.ReplyDTO;

public interface LikeService {
	List<ReplyDTO> selectAllLikeByUserCodeFk(int userCodeFk);
}
