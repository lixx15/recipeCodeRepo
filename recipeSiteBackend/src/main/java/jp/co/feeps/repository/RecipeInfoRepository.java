package jp.co.feeps.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Recipe;
import jp.co.feeps.model.Tags;

// To get about recipe details
@Repository
public interface RecipeInfoRepository extends JpaRepository<Recipe, Integer>{
	
	Optional<Recipe> findById(int recipe_id);
	
	@Query(value = "SELECT r.id, r.title, r.recipe_description, r.procedure_description, r.user_id "
	        + "FROM recipes r "
	        + "JOIN recipe_tags rt ON r.id = rt.recipe_id "
	        + "JOIN tags t ON rt.tag_id = t.id WHERE t.name IN :tags "
	        + "GROUP BY r.id, r.title, r.recipe_description, r.procedure_description, r.user_id "
	        + "HAVING COUNT(DISTINCT t.name) = :count", nativeQuery = true)
	List<Recipe> findRecipesWithAllTags(@Param("tags") List<String> tags, @Param("count") int count);
	
	
	@Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.tags t WHERE r.title LIKE CONCAT('%', :title, '%')")
    List<Recipe> findByTitleContaining(@Param("title") String title);
	
	@Query("SELECT r.tags FROM Recipe r WHERE r.id = :recipeId")
    List<Tags> findTagsByRecipeId(@Param("recipeId") int recipeId);
	
	List<Recipe> findByUser_UserId(int userId);
}
