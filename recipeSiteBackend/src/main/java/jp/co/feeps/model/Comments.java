package jp.co.feeps.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "post_datetime")
    private LocalDateTime postDatetime;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipes recipe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    // Getters and Setters
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getContent() {
    	return content;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
    
    public LocalDateTime getPostDatetime() {
    	return postDatetime;
    }
    
    public void setPostDatetime(LocalDateTime postDatetime) {
    	this.postDatetime = postDatetime;
    }
    
    public Recipes getRecipe() {
    	return recipe;
    }
    
    public void setRecipe(Recipes recipe) {
    	this.recipe = recipe;
    }
    
    public Users getUser() {
    	return user;
    }
    
    public void setRecipe(Users user) {
    	this.user = user;
    }
}
