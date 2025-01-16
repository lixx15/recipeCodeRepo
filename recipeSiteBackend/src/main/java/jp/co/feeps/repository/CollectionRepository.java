package jp.co.feeps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.feeps.model.Collection;


public interface CollectionRepository extends JpaRepository<Collection, Integer> {
	Optional<Collection> findByRecipeIdAndUserUserId(int recipeId, int userId);
}