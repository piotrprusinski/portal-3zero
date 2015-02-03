package pl.com.agora.portal3.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.agora.article.client.Article;
import pl.com.agora.article.client.ArticleClient;

@Controller
public class ControllerIndex {

	@Autowired
	public ArticleClient articleClient;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ControllerIndex.class);
	
	@RequestMapping("/")
	public String getIndex(Map<String, Object> model) throws InterruptedException, ExecutionException {
		Future<List<Article>> allArticles = articleClient.findAll();
		try {
			model.put("articles", allArticles.get());
		} catch (Exception e) {
			LOGGER.error("cannot get articles" , e);
			return e.getMessage();
		}
		return "index";
	}
}
