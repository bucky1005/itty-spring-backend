package org.iot.itty.article.vo;

import lombok.Data;

@Data
public class ResponseSelectAllLikeByUserCodeFk {
	private int likeCodePk;
	private int userCodeFk;
	private int replyCodeFk;
}
