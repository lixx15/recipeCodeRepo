package jp.co.feeps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.AccountDto;
import jp.co.feeps.DTO.CommentsDto;
import jp.co.feeps.DTO.RecipeDto;
import jp.co.feeps.model.Recipe;
import jp.co.feeps.service.CollectionService;
import jp.co.feeps.service.CommentService;
import jp.co.feeps.service.RecipeService;

//http://localhost:15151/recipe_cite/findRecipeByTag?tags=tag1&tags=tag2
//http://localhost:15151/recipe_cite/getRecipeAll
//http://localhost:15151/recipe_cite/getRecipeDetails?recipe_id=1&user_id=1

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeServer;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CollectionService CollecttService;
	
	@RequestMapping("/getRecipeAll")
	public ResponseEntity<List<Recipe>> GetRecipeAll(HttpSession session) {
		List<Recipe> recipeInfo = recipeServer.getAllrecipes();
		
		return ResponseEntity.ok(recipeInfo);
	}
	
	
	@RequestMapping("/findRecipeByTag")
	public ResponseEntity<List<Recipe>> FindRecipeByTag(@RequestParam List<String> tags, HttpSession session) {
	    List<Recipe> recipeInfo = recipeServer.findByTag(tags);
	    return ResponseEntity.ok(recipeInfo);
	}
	
	@RequestMapping("/findRecipeByTitle")
	public ResponseEntity<List<Recipe>> FindRecipeByTitle(@RequestParam String title, HttpSession session) {
	    List<Recipe> recipeInfo = recipeServer.findByTittle(title);
	    return ResponseEntity.ok(recipeInfo);
	}
	
	@RequestMapping("/getRecipeDetails")
	public ResponseEntity<RecipeDto> GetRecipeDetails(@RequestParam int recipe_id, @RequestParam int user_id , HttpSession session) {
		RecipeDto res1 = recipeServer.getRecipeDetails(recipe_id, user_id);
		return ResponseEntity.ok(res1);
	}
	
	@PostMapping("/subbmitComment")
	public ResponseEntity<List<Recipe>> SubbmitComment(@RequestBody CommentsDto newComment, HttpSession session) {
		AccountDto user = (AccountDto) session.getAttribute("registeredUser");
		if(commentService.submitComment(newComment, user.getUserId()))
			return ResponseEntity.ok().build();
		// failed log
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("/recipeCollected")
	public ResponseEntity<Integer> RecipeCollected(@RequestParam String datetime, int recipe_id, HttpSession session) {
		AccountDto userInfo = (AccountDto) session.getAttribute("registeredUser");
		System.out.println(userInfo.getUserId());
	    int res = CollecttService.submitComment(datetime, recipe_id, userInfo.getUserId());
	    return ResponseEntity.ok(res);
	}
	
	@RequestMapping("/getSimilarRecipes")
	public ResponseEntity<List<RecipeDto>> GetSimilarRecipes(@RequestParam List<String> tags , HttpSession session) {
		List<RecipeDto> res = recipeServer.GetSimilarRecipes(tags);
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/add-recipe-with-detail")
	public ResponseEntity<String> addRecipeWithDetails(@RequestBody RecipeDto recipeDto) {
	    Recipe recipe = recipeServer.addRecipeWithDetails(recipeDto);
	    return ResponseEntity.ok("Recipe added successfully with ID: " + recipe.getId());
	}
	
    @PutMapping("/update-recipe")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody RecipeDto recipeDto) {
        try {
            // レシピを更新
            Recipe updatedRecipe = recipeServer.updateRecipe(recipeDto);
            return ResponseEntity.ok(updatedRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    

//	//TO DO test code	
//	@GetMapping("/test")
//	public void Findtest(HttpSession session) {
//		List<String> tagList = new ArrayList<String>();
//		tagList.add("tag1");
//		//tagList.add("tag2");
//		//tagList.add("tag3");
//		List<Recipe> res = recipeServer.findByTag(tagList);
//		
//		System.out.println("test1 running");
//		for (Recipe recipe : res) {
//		    System.out.println(recipe.getTitle());
//		}
//	}
//	
//	@GetMapping("/test2")
//	public void Findtest2(HttpSession session) {
//		System.out.println("test2 running");
//		List<Recipe> res = recipeServer.getAllrecipes();
//		for (Recipe recipe : res) {
//		    System.out.println(recipe.getTitle());
//		}
//	}
//	
//	
//	// filling user info
//	@GetMapping("/test5")
//	public void fillingInfo(HttpSession session) {
//		AccountDto updatedUser = new AccountDto();
//		updatedUser.setUserId(1);
//		updatedUser.setUserName("user_1");
//		updatedUser.setEmail("456456@456465");
//		updatedUser.setPassword("118");
//
//		// セッションに更新したユーザー情報を保存
//		session.setAttribute("registeredUser", updatedUser);
//	}
//	
//	@GetMapping("/test3")
//	public void Findtest3(HttpSession session) {
//		List<String> tagList = new ArrayList<String>();
//		//accountDto user = (accountDto) session.getAttribute("registeredUser");
//		AccountDto user = new AccountDto();
//		user.setUserId(1);
//		tagList.add("tag1");
//		//tagList.add("tag2");
//		//tagList.add("tag3");
//		List<Recipe> res = recipeServer.findByTag(tagList);
//		RecipeDetailsDTO res1 = recipeServer.getRecipeDetails(res.get(0).getId(), user.getUserId());
//		System.out.println("test3 running");
//		System.out.println(res1.getTitle());
//		for (TagDto tagone : res1.getTags()) {
//		    System.out.println(tagone.getName());
//		}
//		System.out.println(res1.getIngredients().size());
//		for (IngredientsDto inone : res1.getIngredients()) {
//		    System.out.println(inone.getName());
//		}
//		
//	}
	
}
