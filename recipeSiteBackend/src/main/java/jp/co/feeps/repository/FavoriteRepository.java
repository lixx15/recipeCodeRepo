package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Favorites;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Integer> {
	List<Favorites> findByUser_UserId(int userId);
	
	List<Favorites> findByRecipeId(int recipeId);
}
