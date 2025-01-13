package jp.co.feeps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Tags;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {
    
}
