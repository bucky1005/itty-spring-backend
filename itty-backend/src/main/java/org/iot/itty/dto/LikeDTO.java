package org.iot.itty.dto;

import lombok.Data;

@Data
public class LikeDTO {
	private int likeCodePk;
	private int userCodeFk;
	private int replyCodeFk;
}
