package jp.co.feeps.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.model.Collection;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.CollectionRepository;
import jp.co.feeps.repository.RecipeInfoRepository;
import jp.co.feeps.repository.UserRepository;

@Service
public class CollectionService {
	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private RecipeInfoRepository recipeinfoRepository;
	@Autowired
	private UserRepository userRepository;
	
	public int submitComment(String datetime, int recipe_id, int user_id) {
		// report null
		Optional<Recipe> recipeAvailable = recipeinfoRepository.findById(recipe_id);
		if (!recipeAvailable.isPresent())
			return 1;
		
		Optional<Users> userAvailable = userRepository.findById(user_id);
        if (!userAvailable.isPresent()) {
            return 2; 
        }
		
		Optional<Collection> result = collectionRepository.findByRecipeIdAndUserUserId(recipe_id, user_id);
		if (result.isPresent()) {
            // if found, delete
            collectionRepository.delete(result.get());
            return 0;
        } else {
            // else insert one record
            Collection newCollection = new Collection();
            newCollection.setFavorite_datetime(datetime);
            newCollection.setRecipe(recipeAvailable.get());
            newCollection.setUser(userAvailable.get());
            collectionRepository.save(newCollection);
            return 0; 
        }
	}
}
