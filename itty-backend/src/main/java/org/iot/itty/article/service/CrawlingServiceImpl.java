package org.iot.itty.article.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.iot.itty.article.aggregate.CrawledArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class CrawlingServiceImpl implements CrawlingService{

	private static final String URL = "https://www.bloter.net/news/articleList.html?page=1&total=33475&box_idxno=&sc_section_code=S1N4&view_type=sm";

	@PostConstruct
	@Override
	public List<CrawledArticle> getTrendNews() throws IOException {
		List<CrawledArticle> articleList = new ArrayList<>();
		Document document = Jsoup.connect(URL).get();
		Elements contents = document.select("section ul.type2 li");

		for (Element content : contents) {
			CrawledArticle crawledArticle = CrawledArticle.builder()
				.image(content.select("a img").attr("abs:src"))
				.subject(content.select("h2 a").text())
				.url(content.select("a").attr("abs:href"))
				.content(content.select("p a").text())
				.build();
			articleList.add(crawledArticle);
		}

		return articleList;
	}
}
