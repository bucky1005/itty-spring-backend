package org.iot.itty.article.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reply_like_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReplyLikeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reply_like_code_pk")
	private Integer likeCodePk;

	@Column(name = "user_code_fk")
	private Integer userCodeFk;

	@Column(name = "reply_code_fk")
	private Integer replyCodeFk;
}
