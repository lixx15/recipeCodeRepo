package jp.co.feeps.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MypageDto {
	
	/* 
	 * accountDto及びRecipeDtoの二つの情報を使う
	 * どちらかに記述するのでは可読性が落ちると考え独自にMypageDtoを作成
	*/
	private int userId;
	private String userName;
	private String email;
	private List<RecipeDto> postedRecipes; 
	private List<RecipeDto> favoriteRecipes;
	private Map<LocalDateTime, String> timeLineItem;
	
    // Getter and Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RecipeDto> getPostedRecipes() {
        return postedRecipes;
    }

    public void setPostedRecipes(List<RecipeDto> postedRecipes) {
        this.postedRecipes = postedRecipes;
    }

    public List<RecipeDto> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<RecipeDto> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
    
    public Map<LocalDateTime, String> getTimeLineItem() {
        return timeLineItem;
    }

    public void setTimeLineItem(Map<LocalDateTime, String> timeLineItem) {
        this.timeLineItem = timeLineItem;
    }
}
