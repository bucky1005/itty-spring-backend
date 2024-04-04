package org.iot.itty.article.aggregate;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_tb")
@Data
public class ArticleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_code_pk")
	private Integer articleCodePk;

	@Column(name = "article_title")
	private String articleTitle;

	@Column(name = "article_content")
	private String articleContent;

	@Column(name = "article_created_date")
	private Date articleCreatedDate;

	@Column(name = "article_last_updated_date")
	private Date articleLastUpdatedDate;

	@Column(name = "user_code_fk")
	private int userCodeFk;

	@Column(name = "article_category")
	private int articleCategory;

	@Column(name = "article_view_count")
	private int articleViewCount;
}
