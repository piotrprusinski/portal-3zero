package pl.com.agora.portal3.model;

public class CommentDto {

	private String id;
	private String content;
	private String articleId;

	public CommentDto(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public CommentDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
}
