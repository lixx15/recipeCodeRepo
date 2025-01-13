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
@Table(name = "favorites")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "favorite_datetime")
    private LocalDateTime favoriteDatetime;

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

    public LocalDateTime getFavoriteDatetime() {
        return favoriteDatetime;
    }

    public void setFavoriteDatetime(LocalDateTime favoriteDatetime) {
        this.favoriteDatetime = favoriteDatetime;
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

    public void setUser(Users user) {
        this.user = user;
    }
}
