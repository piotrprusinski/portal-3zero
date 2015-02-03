package pl.com.agora.portal3.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.com.agora.api.client.rest.UriInvocationException;
import pl.com.agora.article.client.Article;
import pl.com.agora.article.client.ArticleClient;

@Controller
public class ControllerIndex {

	@Autowired
	public ArticleClient articleClient;

	@RequestMapping("/")
	public String getIndex(Map<String, Object> model)
			throws InterruptedException, ExecutionException {
		Future<List<Article>> allArticles = articleClient.findAll();
		model.put("articles", allArticles.get());
		return "index";
	}

	@ExceptionHandler(UriInvocationException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String returnErrorPage(UriInvocationException exception, Map<String, Object> model) {
		model.put("message", exception.getMessage() + " : " + exception.getResult());
		return "error";
	}
}
