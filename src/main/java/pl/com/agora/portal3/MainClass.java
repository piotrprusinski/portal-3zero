package pl.com.agora.portal3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.com.agora.article.client.ArticleClient;
import pl.com.agora.article.client.ArticleClientFactory;
import pl.com.agora.springboot.comments.CommentClient;
import pl.com.agora.springboot.comments.CommentClientFactory;

@SpringBootApplication
public class MainClass {

	public static void main(String[] args) {
		SpringApplication.run(MainClass.class, args);
	}

	@Bean
	public ArticleClient articleClient(
			@Value("${articles.serviceHost}") String serviceHost,
			@Value("${articles.servicePort}") int servicePort) throws Exception {
		return new ArticleClientFactory().createSimpleArticleClient(
				serviceHost, servicePort);
	}

	@Bean
	public CommentClient commentClient(
			@Value("${comment.serviceHost}") String serviceHost,
			@Value("${comment.servicePort}") int servicePort) throws Exception {
		return new CommentClientFactory().createSimpleCommentClient(
				serviceHost, servicePort);
	}
}
