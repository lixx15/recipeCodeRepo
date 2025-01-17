package jp.co.feeps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.feeps.model.Tags;


public interface TagRepository extends JpaRepository<Tags, Integer>{
	Optional<Tags> findByName(String name);
} 