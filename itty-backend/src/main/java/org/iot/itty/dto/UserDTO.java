package org.iot.itty.dto;

import java.util.List;

import org.iot.itty.article.vo.ResponseSelectAllArticleByUserCodeFk;
import org.iot.itty.article.vo.ResponseSelectAllReplyByUserCodeFk;

import lombok.Data;

@Data
public class UserDTO {
	private int userCodePk;
	private String userEmail;
	private String userName;
	private String userPhoneNumber;
	private String userRole;
	private String userNickname;
	private String userPassword;
	private String userIntroduction;
	private int userDeleteStatus;
	private List<ResponseSelectAllArticleByUserCodeFk> articleDTOList;
	private List<ResponseSelectAllReplyByUserCodeFk> replyDTOList;
}
