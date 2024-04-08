package org.iot.itty.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private int replyCodePk;
	private String replyContent;
	private int userCodeFk;
	private int articleCodeFk;
	private Date replyCreatedDate;
	private Date replyLastUpdatedDate;
	private UserDTO userDTO;
}
