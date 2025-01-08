package jp.co.feeps.DTO;

public class CommentsDto {
	
	private String content;
	private String post_datetime;
	private int recipe_id;
	
	public CommentsDto(String one_content, String post_datetime) {
        this.content = one_content;
        this.post_datetime = post_datetime;
    }
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPost_datetime() {
		return post_datetime;
	}
	public void setPost_datetime(String post_datetime) {
		this.post_datetime = post_datetime;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	
	
}
