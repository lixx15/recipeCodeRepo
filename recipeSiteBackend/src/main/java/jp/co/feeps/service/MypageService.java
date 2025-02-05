package jp.co.feeps.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jp.co.feeps.DTO.AccountDto;
import jp.co.feeps.DTO.MypageDto;
import jp.co.feeps.DTO.RecipeDto;
import jp.co.feeps.model.Comment;
import jp.co.feeps.model.Favorites;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.repository.CommentRepository;
import jp.co.feeps.repository.FavoriteRepository;
import jp.co.feeps.repository.RecipeInfoRepository;

@Service
public class MypageService {
    @Autowired
    private RecipeInfoRepository recipeInfoRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    /**
     * マイページデータを取得する
     * @param user ログイン中のユーザー情報
     * @return MypageDto（ユーザー情報、投稿レシピ、タイムラインなど）
     */
    public MypageDto getUserMypage(AccountDto user) {
    	// ログインユーザIDをもとに検索を行うためIDは変数に保持
    	int userId = user.getUserId();
        
        // ログインユーザーが投稿したレシピを取得しRecipeDtoに変換
        List<RecipeDto> postedRecipes = Optional.ofNullable(recipeInfoRepository.findByUser_UserId(userId))
        										.orElse(Collections.emptyList()) // nullの場合は空のリストを代入
												.stream()
												.map(recipe -> convertToRecipeDto(recipe))
												.collect(Collectors.toList());
        
        // ログインユーザーがお気に入りしたレシピを取得しRecipeDtoに変換
        List<RecipeDto> favoriteRecipes = Optional.ofNullable(favoriteRepository.findByUser_UserId(userId))
        										.orElse(Collections.emptyList())// nullの場合は空のリストを代入
												.stream()
												.map(favorite -> convertToRecipeDto(favorite.getRecipe()))
												.collect(Collectors.toList());
        

        // ログインユーザーのタイムラインを作成
        Map<String, String> timeLine = createTimeLine(userId);
        
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
     * @return recipeDto（レシピの基本情報及び、レシピが持つコメント数、お気に入り数など）
     */
    public RecipeDto convertToRecipeDto(Recipe recipe) {
    	RecipeDto recipesDto = new RecipeDto();
    	
    	// レシピIdでコメントの取得を行うためIDを保持
    	int recipeId = recipe.getId();
        recipesDto.setRecipe_id(recipeId);
        recipesDto.setTitle(recipe.getTitle());
        recipesDto.setRecipe_description(recipe.getRecipe_description());
        
        // レシピが持つコメントの取得を行いnullチェック後コメント数をセット
        List<Comment> postedComments = commentRepository.findByRecipeId(recipeId);
        if (CollectionUtils.isEmpty(postedComments)) {
        	recipesDto.setCommentCount(0);
        } else {
        	recipesDto.setCommentCount(postedComments.size());
        }
        
        // レシピが持つお気に入りの取得を行いnullチェック後お気に入り数をセット
        List<Favorites> favoritedItem = favoriteRepository.findByRecipeId(recipeId);
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
    public Map<String, String> createTimeLine(int userId) {
        Map<String, String> timeLineMap = new TreeMap<>(Collections.reverseOrder());

        // ユーザーが投稿したレシピに対するコメントを収集
        List<Recipe> userRecipes = recipeInfoRepository.findByUser_UserId(userId);
        for (Recipe recipe : userRecipes) {
            List<Comment> comments = commentRepository.findByRecipeId(recipe.getId());
            for (Comment comment : comments) {
                String message = "「" + recipe.getTitle() + "」に " + comment.getUser().getUserName() + " さんがコメントしました: ";
                timeLineMap.put(comment.getPost_datetime(), message);
            }
        }

        // ユーザーが投稿したレシピに対するお気に入りを収集
        for (Recipe recipe : userRecipes) {
            List<Favorites> favorites = favoriteRepository.findByRecipeId(recipe.getId());
            for (Favorites favorite : favorites) {
                String message = "「" + recipe.getTitle() + "」が " + favorite.getUser().getUserName() + " さんにお気に入り登録されました";
                timeLineMap.put(favorite.getFavoriteDatetime(), message);
            }
        }

        return timeLineMap;
    }

}
