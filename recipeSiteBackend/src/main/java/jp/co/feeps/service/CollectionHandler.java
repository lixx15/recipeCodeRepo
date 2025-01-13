package jp.co.feeps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.model.Collection;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.CollectionRepository;
import jp.co.feeps.repository.RecipeInfoRepository;
import jp.co.feeps.repository.UserRepository;

@Service
public class CollectionHandler {
	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private RecipeInfoRepository recipeinfoRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Boolean submitComment(String datetime, int recipe_id, int userId) {
		// report null
		Users user = userRepository.findById(userId).get();;
		Recipe recipe = recipeinfoRepository.findById(recipe_id).get();

		Collection newCollection = new Collection();
		newCollection.setFavorite_datetime(datetime);
		newCollection.setRecipe(recipe);
		newCollection.setUser(user);
		collectionRepository.save(newCollection);
		return true;
	}
}
