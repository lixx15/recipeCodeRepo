package jp.co.feeps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.DTO.CollectionDto;
import jp.co.feeps.DTO.CommentsDto;
import jp.co.feeps.DTO.IngredientsDto;
import jp.co.feeps.DTO.RecipeDto;
import jp.co.feeps.DTO.TagDto;
import jp.co.feeps.model.Collection;
import jp.co.feeps.model.Comment;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.model.RecipeIngredients;
import jp.co.feeps.model.Tags;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.CollectionRepository;
import jp.co.feeps.repository.CommentRepository;
import jp.co.feeps.repository.IngredientRepository;
import jp.co.feeps.repository.RecipeInfoRepository;
import jp.co.feeps.repository.TagRepository;
import jp.co.feeps.repository.UserRepository;

@Service
public class RecipeService {
	@Autowired
	private RecipeInfoRepository recipeinfoRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	// 一覧画面用
	public List<Recipe> getAllrecipes() {
		List<Recipe> allRecipes = recipeinfoRepository.findAll();
		return allRecipes;
	}
	
	//タッグ検索
	public List<Recipe> findByTag(List<String> tagList) {
		List<Recipe> result = recipeinfoRepository.findRecipesWithAllTags(tagList, tagList.size());
		return result;
	}
	
	//タイトル検索用
	public List<Recipe> findByTittle(String Tittle) {
		List<Recipe> result = recipeinfoRepository.findByTitleContaining(Tittle);
		return result;
	}
	
	public List<RecipeDto> GetSimilarRecipes(List<String> tags) {
		List<Recipe> resultRiceps = recipeinfoRepository.findRecipesWithAllTags(tags, tags.size());
		if(resultRiceps.size()==0 || (resultRiceps.size()<5 && tags.size()>1) )
			tags.remove(tags.size() - 1);
		List<Recipe> resultRicepsMi1 = recipeinfoRepository.findRecipesWithAllTags(tags, tags.size());
		
		Set<Integer> existingIds = resultRiceps.stream()
                .map(Recipe::getId)
                .collect(Collectors.toSet());
		List<Recipe> filteredResultRicepsMi1 = resultRicepsMi1.stream()
                .filter(recipe -> !existingIds.contains(recipe.getId()))
                .collect(Collectors.toList());

		// add filtered resultRiceps
		resultRiceps.addAll(filteredResultRicepsMi1);
		
		return resultRiceps.stream()
                .map(recipe -> new RecipeDto(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipe.getProcedure_description()
                ))
                .collect(Collectors.toList());
	}
	
	public Recipe addRecipe(String name, String description, String procedure_description) {
        Recipe newRecipe = new Recipe();
        newRecipe.setTitle(name);
        newRecipe.setRecipe_description(description);
        newRecipe.setProcedure_description(procedure_description);
        return recipeinfoRepository.save(newRecipe);
    }
	
	//filling all recipe's details(comment, collection, tag, ingreientm user)
	public RecipeDto getRecipeDetails(int recipe_id, int user_id) {
		Recipe recipe = recipeinfoRepository.findById(recipe_id).get();
		RecipeDto dto = new RecipeDto();
		// 更新ボタン表示のためレシピ投稿ユーザーの取得を行う
		dto.setUserId(recipe.getUser().getUserId());
		dto.setRecipeId(recipe.getId());
		dto.setTitle(recipe.getTitle());
        dto.setRecipeDescription(recipe.getRecipe_description());
        dto.setProcedureDescription(recipe.getProcedure_description());

        List<Tags> tags = recipeinfoRepository.findTagsByRecipeId(recipe_id);
        if(tags != null)
        {
	        List<TagDto> tagDTOs = new ArrayList<>();
	        for (Tags tag : tags) {
	            TagDto tagDTO = new TagDto(tag.getName());
	            tagDTOs.add(tagDTO);
	        }
	        dto.setTags(tagDTOs);
        }

        List<RecipeIngredients> ingredients = ingredientRepository.findByRecipeId(recipe_id);
        if(ingredients != null)
        {
	        List<IngredientsDto> inDTOs = new ArrayList<>();
	        for (RecipeIngredients ins : ingredients) {
	        	IngredientsDto inone = new IngredientsDto(ins.getTitle(), ins.getQuantity());
	        	inDTOs.add(inone);
	        }
	        dto.setIngredients(inDTOs);
        }

        List<Comment> comments = commentRepository.findByRecipeId(recipe_id);
        if(comments != null)
        {
	        List<CommentsDto> coDTOs = new ArrayList<>();
	        for (Comment cos : comments) {
	        	CommentsDto coone = new CommentsDto(cos.getContent(), cos.getPost_datetime());
	        	coDTOs.add(coone);
	        }
	        dto.setComments(coDTOs); 
        }

        Optional<Collection> optionalCollection  = collectionRepository.findByRecipeIdAndUserUserId(recipe_id, user_id);
        if (optionalCollection.isPresent()) {
            Collection collecionInfo = optionalCollection.get();
            CollectionDto collection_info = new CollectionDto(collecionInfo.getFavorite_datetime());
            dto.setCollection_info(collection_info);
        } else {
            System.out.println("No collection found for given recipe ID and user ID");
        }

		return dto;
	}
	
	public Recipe addRecipeWithDetails(RecipeDto recipeDto) {
		// 入力情報をエンティティにセット
	    Recipe newRecipe = new Recipe();
	    String d = recipeDto.getRecipeDescription();
	    String p = recipeDto.getProcedureDescription();
	    newRecipe.setTitle(recipeDto.getTitle());
	    newRecipe.setRecipe_description(recipeDto.getRecipeDescription());
	    newRecipe.setProcedure_description(recipeDto.getProcedureDescription());

	    // ユーザー情報をセッションから取得
	    Users user = userRepository.findById(recipeDto.getUserId()).get();
	    newRecipe.setUser(user);
	    
	    // タグを取得または作成してセットに追加
	    // タグを保存
	    List<String> tagNames = recipeDto.getTags().stream()
	    											.map(tag -> tag.getName())
	    											.collect(Collectors.toList());
	    
	    Set<Tags> tags = tagNames.stream()
	            .map(tagName -> {
	                Optional<Tags> optionalTag = tagRepository.findByName(tagName);
	                if (optionalTag.isPresent()) {
	                    // タグが存在する場合は取得
	                    return optionalTag.get();
	                } else {
	                    // タグが存在しない場合は新規作成
	                    Tags newTag = new Tags();
	                    newTag.setName(tagName);
	                    return tagRepository.save(newTag);
	                }
	            })
	            .collect(Collectors.toSet());

	    // レシピにタグをセット
	    newRecipe.setTags(tags);

	    // レシピを保存（IDが必要なので先に保存）
	    Recipe savedRecipe = recipeinfoRepository.save(newRecipe);

	    // 材料を保存
	    for (IngredientsDto ingredientDTO : recipeDto.getIngredients()) {
	        RecipeIngredients ingredient = new RecipeIngredients();
	        ingredient.setTitle(ingredientDTO.getName());
	        ingredient.setQuantity(ingredientDTO.getQuantity());
	        ingredient.setRecipe(savedRecipe);
	        ingredientRepository.save(ingredient);
	    }

	    return savedRecipe;
	}
	
    public Recipe updateRecipe(RecipeDto recipeDto) throws Exception {
        // 既存のレシピを取得
        Optional<Recipe> existingRecipe = recipeinfoRepository.findById(recipeDto.getRecipeId());
        if (!existingRecipe.isPresent()) {
            throw new Exception("レシピが見つかりません");
        }

        // レシピの更新
        Recipe updatedRecipe = existingRecipe.get();
        updatedRecipe.setTitle(recipeDto.getTitle());
        updatedRecipe.setRecipe_description(recipeDto.getRecipeDescription());
        updatedRecipe.setProcedure_description(recipeDto.getProcedureDescription());

	    // タグを取得または作成してセットに追加
	    // タグを保存
	    List<String> tagNames = recipeDto.getTags().stream()
	    											.map(tag -> tag.getName())
	    											.collect(Collectors.toList());
	    
	    Set<Tags> tags = tagNames.stream()
	            .map(tagName -> {
	                Optional<Tags> optionalTag = tagRepository.findByName(tagName);
	                if (optionalTag.isPresent()) {
	                    // タグが存在する場合は取得
	                    return optionalTag.get();
	                } else {
	                    // タグが存在しない場合は新規作成
	                    Tags newTag = new Tags();
	                    newTag.setName(tagName);
	                    return tagRepository.save(newTag);
	                }
	            })
	            .collect(Collectors.toSet());

	    // レシピにタグをセット
	    updatedRecipe.setTags(tags);

        // レシピの更新保存
        return recipeinfoRepository.save(updatedRecipe);
    }
    
}

