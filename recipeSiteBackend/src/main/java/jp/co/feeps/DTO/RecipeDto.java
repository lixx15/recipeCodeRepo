package jp.co.feeps.DTO;

public class RecipeDto {
	
	private int recipe_id;
	private String title;
	private String recipe_description;
    private String procedureDescription; // 手順説明
    private String authorName; // 投稿者名
    private int commentCount; //コメント数
    private int favoriteCount; // お気に入り数
	
    public RecipeDto(int id, String title, String recipe_description)
    {
    	this.recipe_id = id;
    	this.title = title;
    	this.recipe_description = recipe_description;
    }
	public RecipeDto() {
		// TODO Auto-generated constructor stub
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecipe_description() {
		return recipe_description;
	}
	public void setRecipe_description(String recipe_description) {
		this.recipe_description = recipe_description;
	}
	
    public String getProcedureDescription() {
        return procedureDescription;
    }
    public void setProcedureDescription(String procedureDescription) {
        this.procedureDescription = procedureDescription;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public int getFavoriteCount() {
        return favoriteCount;
    }
    
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
    
    public int getcommentCount() {
        return commentCount;
    }
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
	
	
}
