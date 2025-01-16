package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.feeps.model.Comment;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	List<Comment> findByRecipeIdAndUserUserId(int recipeId, int userId);

	List<Comment> findByRecipeId(int recipeId);
}