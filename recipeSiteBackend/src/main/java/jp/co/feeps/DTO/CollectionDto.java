package jp.co.feeps.DTO;

public class CollectionDto {
	private String favorite_datetime;
	private int recipe_id;
	
	public CollectionDto(String favorite_datetime) {
        this.favorite_datetime = favorite_datetime;
    }
	
	
	public String getFavorite_datetime() {
		return favorite_datetime;
	}
	public void setFavorite_datetime(String favorite_datetime) {
		this.favorite_datetime = favorite_datetime;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	
	
}
