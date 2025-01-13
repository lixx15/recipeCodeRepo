package jp.co.feeps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Ingredients;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredients, Integer> {
    
}
