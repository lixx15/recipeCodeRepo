package jp.co.feeps.DTO;

import java.util.List;

public class RecipeDto {
	
	private int recipeId;
	private int userId;
	private String title;
	private String recipeDescription;
    private String procedureDescription;
    private int commentCount; //コメント数
    private int favoriteCount; // お気に入り数
    private List<TagDto> tags;
    private List<IngredientsDto> ingredients;
    private List<CommentsDto> comments;
    private CollectionDto collection_info;
	
    
    public RecipeDto(int recipeId, String title, String recipeDescription)
    {
    	this.recipeId = recipeId;
    	this.title = title;
    	this.recipeDescription = recipeDescription;
    }
	public RecipeDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getRecipeDescription() {
		return recipeDescription;
	}
	
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	
    public String getProcedureDescription() {
        return procedureDescription;
    }
    
    public void setProcedureDescription(String procedureDescription) {
        this.procedureDescription = procedureDescription;
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
    
	public List<TagDto> getTags() {
		return tags;
	}
	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}
	
	public List<IngredientsDto> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<IngredientsDto> materials) {
		this.ingredients = materials;
	}
	
	public List<CommentsDto> getComments() {
		return comments;
	}
	
	public void setComments(List<CommentsDto> comments) {
		this.comments = comments;
	}
	
	public CollectionDto getCollection_info() {
		return collection_info;
	}
	
	public void setCollection_info(CollectionDto collection_info) {
		this.collection_info = collection_info;
	}
	
	
}
