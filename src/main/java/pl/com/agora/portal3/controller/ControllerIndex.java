package pl.com.agora.portal3.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.agora.article.client.Article;
import pl.com.agora.article.client.ArticleClient;

@Controller
public class ControllerIndex {

	@Autowired
	public ArticleClient articleClient;
	
	@RequestMapping("/")
	public String getIndex(Map<String, Object> model) throws InterruptedException, ExecutionException {
		Future<List<Article>> allArticles = articleClient.findAll();
		model.put("articles", allArticles.get());
		return "index";
	}
}
