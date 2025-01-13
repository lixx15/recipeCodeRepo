package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Recipes;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Integer> {
	List<Recipes> findByUser_UserId(int userId);
}
