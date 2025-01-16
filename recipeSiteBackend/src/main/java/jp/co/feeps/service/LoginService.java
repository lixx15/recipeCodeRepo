package jp.co.feeps.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.DTO.LoginDto;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	// ログイン処理
	public LoginDto loginUser(String email, String password, HttpSession session) {
		// データからユーザーを捜す
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("ユーザーが存在しません"));

		// パスワードマッチング
		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("パスワードが正しくありません");
		}

		// 创建返回的DTO并保存用户信息到会话中
		LoginDto loggedInUser = new LoginDto();
		loggedInUser.setUserId(user.getUserId());
		loggedInUser.setUserName(user.getUserName());
		loggedInUser.setEmail(user.getEmail());
		loggedInUser.setPassword(user.getPassword());

		session.setAttribute("loggedInUser", loggedInUser);

		return loggedInUser;
	}

	// ログアウト処理
	public void logoutUser(HttpSession session) {
		session.removeAttribute("loggedInUser"); // ユーザー情報を除去
		session.invalidate(); //セッションを無効
	}
}
