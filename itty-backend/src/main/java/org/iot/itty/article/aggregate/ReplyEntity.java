package org.iot.itty.article.aggregate;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyEntity {
	private Integer replyCodePk;
	private String replyContent;
	private Integer userCodeFk;
	private Integer articleCodeFk;
	private Date replyCreatedDate;
	private Date replyLastUpdatedDate;
}
