package jp.co.feeps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.DTO.CollectionDto;
import jp.co.feeps.DTO.CommentsDto;
import jp.co.feeps.DTO.IngredientsDto;
import jp.co.feeps.DTO.RecipeDetailsDTO;
import jp.co.feeps.DTO.TagDto;
import jp.co.feeps.model.Collection;
import jp.co.feeps.model.Comment;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.model.RecipeIngredients;
import jp.co.feeps.model.Tags;
import jp.co.feeps.repository.CollectionRepository;
import jp.co.feeps.repository.CommentRepository;
import jp.co.feeps.repository.IngredientRepository;
import jp.co.feeps.repository.RecipeInfoRepository;

@Service
public class recipeHandler {
	@Autowired
	private RecipeInfoRepository recipeinfoRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CollectionRepository collectionRepository;
	
	
	public List<Recipe> getAllrecipes() {
		List<Recipe> allRecipes = recipeinfoRepository.findAll();
		return allRecipes;
	}
	
	
	public List<Recipe> findByTag(List<String> tagList) {
		List<Recipe> result = recipeinfoRepository.findRecipesWithAllTags(tagList, tagList.size());
		return result;
	}
	
	
	public List<Recipe> findByTittle(String Tittle) {
		List<Recipe> result = recipeinfoRepository.findByTitleContaining(Tittle);
		return result;
	}

	
	public Recipe addRecipe(String name, String description, String procedure_description) {
        Recipe newRecipe = new Recipe();
        newRecipe.setTitle(name);
        newRecipe.setRecipe_description(description);
        newRecipe.setProcedure_description(procedure_description);
        return recipeinfoRepository.save(newRecipe);
    }
	
	public RecipeDetailsDTO getRecipeDetails(int recipe_id, int user_id) {
		Recipe recipe = recipeinfoRepository.findById(recipe_id).get();
		RecipeDetailsDTO dto = new RecipeDetailsDTO();
		dto.setId(recipe.getId());
		dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getProcedure_description());

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

        List<Comment> comments = commentRepository.findByRecipeIdAndUserUserId(recipe_id, user_id);
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
	
	
}

