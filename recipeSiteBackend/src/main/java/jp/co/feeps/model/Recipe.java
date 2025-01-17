package jp.co.feeps.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "recipe_description")
	private String recipe_description;
	
	@Column(name = "procedure_description")
	private String procedure_description;
	
	@ManyToMany
    @JoinTable(
        name = "recipe_tags",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tags> tags;
	
	@OneToMany(mappedBy = "recipe")
    private List<RecipeIngredients> ingredients;
	
    @ManyToOne
    @JoinColumn(name = "user_id") // 修正: カラム名に合わせる
    private Users user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecipe_description() {
		return recipe_description;
	}

	public void setRecipe_description(String recipe_description) {
		this.recipe_description = recipe_description;
	}

	public String getProcedure_description() {
		return procedure_description;
	}

	public void setProcedure_description(String procedure_description) {
		this.procedure_description = procedure_description;
	}
	
    public Users getUser() {
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
	
    public Set<Tags> getTags() {
        return tags;
    }
    
    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }
}
