package org.iot.itty.article.aggregate;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reply_tb")
public class ReplyEntity {
	@Id
	@Column(name = "reply_code_pk")
	private Integer replyCodePk;

	@Column(name = "reply_content")
	private String replyContent;

	@Column(name = "user_code_fk")
	private Integer userCodeFk;

	@Column(name = "article_code_fk")
	private Integer articleCodeFk;

	@Column(name = "reply_created_date")
	private Date replyCreatedDate;

	@Column(name = "reply_last_updated_date")
	private Date replyLastUpdatedDate;
}
