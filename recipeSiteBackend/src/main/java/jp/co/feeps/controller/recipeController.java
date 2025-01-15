package jp.co.feeps.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.CommentsDto;
import jp.co.feeps.DTO.IngredientsDto;
import jp.co.feeps.DTO.RecipeDetailsDTO;
import jp.co.feeps.DTO.TagDto;
import jp.co.feeps.DTO.accountDto;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.service.CommentHandler;
import jp.co.feeps.service.recipeHandler;

//http://localhost:15151/recipe_cite/findRecipeByTag?tags=tag1&tags=tag2
//http://localhost:15151/recipe_cite/getRecipeAll
//http://localhost:15151/recipe_cite/getRecipeDetails?recipe_id=1&user_id=1

@RestController
@CrossOrigin
public class recipeController {
	
	@Autowired
	private recipeHandler recipeServer;
	
	@Autowired
	private CommentHandler commentHandler;
	
	@RequestMapping("/getRecipeAll")
	public ResponseEntity<List<Recipe>> Register(HttpSession session) {
		List<Recipe> recipeInfo = recipeServer.getAllrecipes();
		
		return ResponseEntity.ok(recipeInfo);
	}
	
	
	@RequestMapping("/findRecipeByTag")
	public ResponseEntity<List<Recipe>> findRecipeByTag(@RequestParam List<String> tags, HttpSession session) {
	    List<Recipe> recipeInfo = recipeServer.findByTag(tags);
	    return ResponseEntity.ok(recipeInfo);
	}
	
	@RequestMapping("/findRecipeByTitle")
	public ResponseEntity<List<Recipe>> findRecipeByTitle(@RequestParam String title, HttpSession session) {
	    List<Recipe> recipeInfo = recipeServer.findByTittle(title);
	    return ResponseEntity.ok(recipeInfo);
	}
	
	@RequestMapping("/getRecipeDetails")
	public ResponseEntity<RecipeDetailsDTO> getRecipeDetails(@RequestParam int recipe_id, @RequestParam int user_id , HttpSession session) {
		RecipeDetailsDTO res1 = recipeServer.getRecipeDetails(recipe_id, user_id);
		return ResponseEntity.ok(res1);
	}
	
	@PostMapping("/subbmitComment")
	public ResponseEntity<List<Recipe>> SubbmitComment(@RequestBody CommentsDto newComment, HttpSession session) {
		accountDto user = (accountDto) session.getAttribute("registeredUser");
		if(commentHandler.submitComment(newComment, user.getUserId()))
			return ResponseEntity.ok().build();
		// failed log
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/test")
	public void Findtest(HttpSession session) {
		List<String> tagList = new ArrayList<String>();
		tagList.add("tag1");
		//tagList.add("tag2");
		//tagList.add("tag3");
		List<Recipe> res = recipeServer.findByTag(tagList);
		
		System.out.println("test1 running");
		for (Recipe recipe : res) {
		    System.out.println(recipe.getTitle());
		}
	}
	
	@GetMapping("/test2")
	public void Findtest2(HttpSession session) {
		System.out.println("test2 running");
		List<Recipe> res = recipeServer.getAllrecipes();
		for (Recipe recipe : res) {
		    System.out.println(recipe.getTitle());
		}
	}
	
	// filling user info
	@GetMapping("/test5")
	public void fillingInfo(HttpSession session) {
		accountDto updatedUser = new accountDto();
		updatedUser.setUserId(1);
		updatedUser.setUserName("user_1");
		updatedUser.setEmail("456456@456465");
		updatedUser.setPassword("118");

		// セッションに更新したユーザー情報を保存
		session.setAttribute("registeredUser", updatedUser);
	}
	
	@GetMapping("/test3")
	public void Findtest3(HttpSession session) {
		List<String> tagList = new ArrayList<String>();
		//accountDto user = (accountDto) session.getAttribute("registeredUser");
		accountDto user = new accountDto();
		user.setUserId(1);
		tagList.add("tag1");
		//tagList.add("tag2");
		//tagList.add("tag3");
		List<Recipe> res = recipeServer.findByTag(tagList);
		RecipeDetailsDTO res1 = recipeServer.getRecipeDetails(res.get(0).getId(), user.getUserId());
		System.out.println("test3 running");
		System.out.println(res1.getTitle());
		for (TagDto tagone : res1.getTags()) {
		    System.out.println(tagone.getName());
		}
		System.out.println(res1.getIngredients().size());
		for (IngredientsDto inone : res1.getIngredients()) {
		    System.out.println(inone.getName());
		}
		
	}
	
}
