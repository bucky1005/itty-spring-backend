package org.iot.itty.article.service;

import java.util.List;

import org.iot.itty.dto.ReplyDTO;

public interface ReplyService {
	List<ReplyDTO> selectReplyByArticleCodeFk(int articleCodePk);

	List<ReplyDTO> selectAllReplyByUserCodeFk(int userCodeFk);
}
