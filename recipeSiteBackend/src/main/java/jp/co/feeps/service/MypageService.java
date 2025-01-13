package jp.co.feeps.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jp.co.feeps.DTO.MypageDto;
import jp.co.feeps.DTO.RecipeDto;
import jp.co.feeps.DTO.accountDto;
import jp.co.feeps.model.Comments;
import jp.co.feeps.model.Favorites;
import jp.co.feeps.model.Recipes;
import jp.co.feeps.repository.CommentRepository;
import jp.co.feeps.repository.FavoriteRepository;
import jp.co.feeps.repository.RecipeRepository;

@Service
public class MypageService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    /**
     * マイページデータを取得する
     * @param session ログイン中のユーザー情報
     * @return MypageDto（ユーザー情報、投稿レシピ、タイムラインなど）
     */
    public MypageDto getUserMypage(accountDto user) {
    	// ログインユーザIDをもとに検索を行うためIDは変数に保持
    	int userId = user.getUserId();
        
        // ログインユーザーが投稿したレシピを取得しRecipeDtoに変換
        List<RecipeDto> postedRecipes = Optional.ofNullable(recipeRepository.findByUser_UserId(userId))
        										.orElse(Collections.emptyList()) // nullの場合は空のリストを代入
												.stream()
												.map(recipes -> convertToRecipeDto(recipes, userId))
												.collect(Collectors.toList());
        
        // ログインユーザーがお気に入りしたレシピを取得しRecipeDtoに変換
        List<RecipeDto> favoriteRecipes = Optional.ofNullable(favoriteRepository.findByUser_UserId(userId))
        										.orElse(Collections.emptyList())// nullの場合は空のリストを代入
												.stream()
												.map(favorite -> convertToRecipeDto(favorite.getRecipe(), userId))
												.collect(Collectors.toList());
        

        // ログインユーザーのタイムラインを作成
        Map<LocalDateTime, String> timeLine = createTimeLine(userId);
        
        MypageDto mypageDto = new MypageDto();
        mypageDto.setUserId(userId);
        mypageDto.setUserName(user.getUserName());
        mypageDto.setEmail(user.getEmail());
        mypageDto.setPostedRecipes(postedRecipes);
        mypageDto.setFavoriteRecipes(favoriteRecipes);
        mypageDto.setTimeLineItem(timeLine);

        return mypageDto;
    }
    
    /**
     * 取得したレシピのデータをDtoにsetする
     * @param recipe 取得したレシピリスト
     * @param userId ログインユーザーのユーザーId
     * @return recipeDto（レシピの基本情報及び、レシピが持つコメント数、お気に入り数など）
     */
    public RecipeDto convertToRecipeDto(Recipes recipe, int userId) {
    	RecipeDto recipesDto = new RecipeDto();
    	
    	// レシピIdでコメントの取得を行うためIDを保持
    	int recipeId = recipe.getId();
        recipesDto.setId(recipeId);
        recipesDto.setTitle(recipe.getTitle());
        recipesDto.setRecipeDescription(recipe.getRecipeDescription());
        
        // レシピが持つコメントの取得を行いnullチェック後コメント数をセット
        List<Comments> postedComments = commentRepository.findByRecipeId(recipeId);
        if (CollectionUtils.isEmpty(postedComments)) {
        	recipesDto.setCommentCount(0);
        } else {
        	recipesDto.setCommentCount(postedComments.size());
        }
        
        // レシピが持つお気に入りの取得を行いnullチェック後お気に入り数をセット
        List<Favorites> favoritedItem = favoriteRepository.findByUser_UserId(userId);
        if (CollectionUtils.isEmpty(favoritedItem)) {
        	recipesDto.setFavoriteCount(0);
        }else {
        	recipesDto.setFavoriteCount(favoritedItem.size());
        }
        
        return recipesDto;
    }
    
    /**
     * ログインユーザーの投稿に対するアクションをMapで取得
     * @param userId ログインユーザーのユーザーId
     * @return timeLineMap<LocalDateTime, String>（keyに日時、valueにログインユーザーに対して行われたアクション）
     */
    public Map<LocalDateTime, String> createTimeLine(int userId) {
        Map<LocalDateTime, String> timeLineMap = new TreeMap<>(Collections.reverseOrder());

        // ユーザーが投稿したレシピに対するコメントを収集
        List<Recipes> userRecipes = recipeRepository.findByUser_UserId(userId);
        for (Recipes recipe : userRecipes) {
            List<Comments> comments = commentRepository.findByRecipeId(recipe.getId());
            for (Comments comment : comments) {
                String message = "「" + recipe.getTitle() + "」に " + comment.getUser().getUserName() + " さんがコメントしました: ";
                timeLineMap.put(comment.getPostDatetime(), message);
            }
        }

        // ユーザーが投稿したレシピに対するお気に入りを収集
        for (Recipes recipe : userRecipes) {
            List<Favorites> favorites = favoriteRepository.findByRecipeId(recipe.getId());
            for (Favorites favorite : favorites) {
                String message = "「" + recipe.getTitle() + "」が " + favorite.getUser().getUserName() + " さんにお気に入り登録されました";
                timeLineMap.put(favorite.getFavoriteDatetime(), message);
            }
        }

        return timeLineMap;
    }

}
