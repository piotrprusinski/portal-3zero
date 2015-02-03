package pl.com.agora.portal3.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.com.agora.article.client.Article;
import pl.com.agora.article.client.ArticleClient;
import pl.com.agora.portal3.model.CommentDto;
import pl.com.agora.springboot.comments.Comment;
import pl.com.agora.springboot.comments.CommentClient;

@Controller
public class ArticleController {

	@Autowired
	public ArticleClient articleClient;
	@Autowired
	public CommentClient commentClient;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArticleController.class);

	@RequestMapping("/article/{id}")
	public String getArticle(@PathVariable("id") String id,
			Map<String, Object> model) throws InterruptedException,
			ExecutionException {
		LOGGER.info("display article");
		Future<Article> article = articleClient.load(id);
		Future<List<Comment>> comments = commentClient.getComments(id);
		CommentDto value = new CommentDto();
		value.setArticleId(article.get().getId());
		model.put("article", article.get());
		model.put("comment", value);
		model.put("comments", comments.get());
		return "article";
	}

	@RequestMapping(value = "/comment/add", method = RequestMethod.POST)
	public String addArticle(CommentDto commentDto)
			throws InterruptedException, ExecutionException {
		Comment comment = new Comment();
		comment.setArticleId(commentDto.getArticleId());
		comment.setAuthor("");
		comment.setContent(commentDto.getContent());
		try {
			Future<Void> putComment = commentClient.putComment(comment, commentDto.getArticleId());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return e.getMessage();
		}

		return "redirect:/article/" + commentDto.getArticleId() + "#comments";
	}
}
