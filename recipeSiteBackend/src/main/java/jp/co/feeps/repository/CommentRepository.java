package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
	List<Comments> findByRecipeId(int recipeId);
}