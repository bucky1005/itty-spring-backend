package org.iot.itty.article.aggregate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrawledArticle {
	private String image;
	private String subject;
	private String url;
	private String content;
}
