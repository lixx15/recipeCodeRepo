package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import jp.co.feeps.model.RecipeIngredients;

public interface IngredientRepository extends JpaRepository<RecipeIngredients, Integer> {
	List<RecipeIngredients> findByRecipeId(int recipeId);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM RecipeIngredients r WHERE r.recipe.id = :recipeId")
    void deleteByRecipeId(int recipeId);
}