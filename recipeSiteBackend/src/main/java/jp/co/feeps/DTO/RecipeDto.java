package jp.co.feeps.DTO;

public class RecipeDto {
	
	private int recipe_id;
	private String title;
	private String recipe_description;
	
	
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
	
	
	
}
