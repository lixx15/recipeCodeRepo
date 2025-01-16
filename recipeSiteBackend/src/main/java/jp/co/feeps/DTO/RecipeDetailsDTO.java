package jp.co.feeps.DTO;

import java.util.List;

public class RecipeDetailsDTO {
    private Integer id;
    private String title;
    private String description;
    private List<TagDto> tags;
    private List<IngredientsDto> ingredients;
    private List<CommentsDto> comments;
    private CollectionDto collection_info;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
