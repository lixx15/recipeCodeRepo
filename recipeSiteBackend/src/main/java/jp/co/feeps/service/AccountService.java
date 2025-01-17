package jp.co.feeps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.AccountDto;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.UserRepository;

@Service
public class AccountService {
	@Autowired
	private UserRepository userRepository;

	// アカウント登録処理
	public AccountDto registerUser(AccountDto accountDto, HttpSession session) {
		//入力チェック
		if (accountDto == null || accountDto.getUserName() == null || accountDto.getEmail() == null
				|| accountDto.getPassword() == null) {
			return null;
		}

		// ユーザー情報をDBに保存
		Users user = new Users();
		user.setUserName(accountDto.getUserName());
		user.setEmail(accountDto.getEmail());
		user.setPassword(accountDto.getPassword());
		userRepository.save(user);

		// 登録したユーザー情報をDTOに変換
		AccountDto registeredUser = new AccountDto();
		registeredUser.setUserId(user.getUserId());
		registeredUser.setUserName(user.getUserName());
		registeredUser.setEmail(user.getEmail());
		registeredUser.setPassword(user.getPassword());

		// セッションに登録したユーザー情報を保存
		session.setAttribute("registeredUser", registeredUser);

		return registeredUser;
	}

	// アカウント更新処理
	public AccountDto updateUser(AccountDto accountDto, HttpSession session) {
		// ユーザー情報をDBから取得
		Users user = userRepository.findById(accountDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("ユーザーが存在しません"));

		// 入力したユーザー情報からDB更新
		user.setUserName(accountDto.getUserName());
		user.setEmail(accountDto.getEmail());
		user.setPassword(accountDto.getPassword());
		userRepository.save(user);

		// 更新したユーザー情報をDTOに変換
		AccountDto updatedUser = new AccountDto();
		updatedUser.setUserId(user.getUserId());
		updatedUser.setUserName(user.getUserName());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setPassword(user.getPassword());

		// セッションに更新したユーザー情報を保存
		session.setAttribute("registeredUser", updatedUser);

		return updatedUser;
	}

	// セッション情報取得
	public AccountDto getSessionUser(HttpSession session) {
		// セッションから"registeredUser"属性を取得
		AccountDto registeredUser = (AccountDto) session.getAttribute("registeredUser");

		// セッションにユーザー情報があるかチェック
		if (registeredUser == null) {
			return null;
		}

		// セッションから取得したユーザー情報を新しいDTOに設定して返す
		AccountDto responseUser = new AccountDto();
		responseUser.setUserId(registeredUser.getUserId());
		responseUser.setUserName(registeredUser.getUserName());
		responseUser.setEmail(registeredUser.getEmail());
		responseUser.setPassword(registeredUser.getPassword());

		return responseUser;
	}
	
	// ログイン処理
	public AccountDto loginUser(String email, String password, HttpSession session) {
		// データからユーザーを捜す
		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("ユーザーが存在しません"));

		// パスワードマッチング
		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("パスワードが正しくありません");
		}

		// 创建返回的DTO并保存用户信息到会话中
		AccountDto loggedInUser = new AccountDto();
		loggedInUser.setUserId(user.getUserId());
		loggedInUser.setUserName(user.getUserName());
		loggedInUser.setEmail(user.getEmail());
		loggedInUser.setPassword(user.getPassword());

		session.setAttribute("registeredUser", loggedInUser);

		return loggedInUser;
	}

	// ログアウト処理
	public void logoutUser(HttpSession session) {
		session.removeAttribute("registeredUser"); // ユーザー情報を除去
		session.invalidate(); //セッションを無効
	}

}