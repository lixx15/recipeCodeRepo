package jp.co.feeps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	// ユーザーIDでユーザー情報を取得するメソッド
	Optional<Users> findById(Integer userId);
}