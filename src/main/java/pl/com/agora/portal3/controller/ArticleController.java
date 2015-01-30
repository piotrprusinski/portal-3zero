package pl.com.agora.portal3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.com.agora.article.client.Article;
import pl.com.agora.article.client.ArticleClient;
import pl.com.agora.portal3.model.CommentDto;

@Controller
public class ArticleController {

	@Autowired
	public ArticleClient articleClient;
	public List<CommentDto> commentList = new ArrayList<CommentDto>() {
		{
			add(new CommentDto("1", "komentarz"));
			add(new CommentDto("2", "komentarz1"));
			add(new CommentDto("3", "komentarz2"));
			add(new CommentDto("4", "komentarz3"));

		}
	};

	@RequestMapping("/article/{id}")
	public String getArticle(@PathVariable("id") String id,
			Map<String, Object> model) throws InterruptedException,
			ExecutionException {
		Future<Article> article = articleClient.load(id);
		
		CommentDto value = new CommentDto();
		value.setArticleId(article.get().getId());
		model.put("article", article.get());
		model.put("comment", value);
		model.put("comments", commentList);
		return "article";
	}

	@RequestMapping(value = "/comment/add", method = RequestMethod.POST)
	public String addArticle(CommentDto commentDto) {
		commentList.add(commentDto);
		return "redirect:/article/" + commentDto.getArticleId();
	}
}
