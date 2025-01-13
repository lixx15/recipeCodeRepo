package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.feeps.model.RecipeIngredients;

public interface IngredientRepository extends JpaRepository<RecipeIngredients, Integer> {
	List<RecipeIngredients> findByRecipeId(int recipeId);
}