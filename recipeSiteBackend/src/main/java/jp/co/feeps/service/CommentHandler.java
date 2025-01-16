package jp.co.feeps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.DTO.CommentsDto;
import jp.co.feeps.model.Comment;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.CommentRepository;
import jp.co.feeps.repository.RecipeInfoRepository;
import jp.co.feeps.repository.UserRepository;

@Service
public class CommentHandler {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RecipeInfoRepository recipeinfoRepository;
	
	public Boolean submitComment(CommentsDto subComment, int userId) {
		// report null
		Users user = userRepository.findById(userId).get();;
		Recipe recipe = recipeinfoRepository.findById(subComment.getRecipe_id()).get();

		Comment newComment = new Comment();
		newComment.setContent(subComment.getContent());
		newComment.setPost_datetime(subComment.getPost_datetime());
		newComment.setRecipe(recipe);
		newComment.setUser(user);
		commentRepository.save(newComment);
		return true;
	}
}
