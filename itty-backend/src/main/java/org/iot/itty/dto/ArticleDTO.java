package org.iot.itty.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleDTO {
	private int articleCodePk;
	private String articleTitle;
	private String articleContent;
	private Date articleCreatedDate;
	private Date articleLastUpdatedDate;
	private int userCode;
	private int articleCategory;
	private int articleViewCount;
}
