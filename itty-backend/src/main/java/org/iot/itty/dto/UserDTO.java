package org.iot.itty.dto;

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
}
