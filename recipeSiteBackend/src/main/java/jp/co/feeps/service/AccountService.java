package jp.co.feeps.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.DTO.accountDto;
import jp.co.feeps.model.Users;
import jp.co.feeps.repository.UserRepository;

@Service
public class AccountService {
	@Autowired
	private UserRepository userRepository;

	// アカウント登録処理
	public accountDto registerUser(accountDto accountDto, HttpSession session) {
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
		accountDto registeredUser = new accountDto();
		registeredUser.setUserId(user.getUserId());
		registeredUser.setUserName(user.getUserName());
		registeredUser.setEmail(user.getEmail());
		registeredUser.setPassword(user.getPassword());

		// セッションに登録したユーザー情報を保存
		session.setAttribute("registeredUser", registeredUser);

		return registeredUser;
	}

	// アカウント更新処理
	public accountDto updateUser(accountDto accountDto, HttpSession session) {
		// ユーザー情報をDBから取得
		Users user = userRepository.findById(accountDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("ユーザーが存在しません"));

		// 入力したユーザー情報からDB更新
		user.setUserName(accountDto.getUserName());
		user.setEmail(accountDto.getEmail());
		user.setPassword(accountDto.getPassword());
		userRepository.save(user);

		// 更新したユーザー情報をDTOに変換
		accountDto updatedUser = new accountDto();
		updatedUser.setUserId(user.getUserId());
		updatedUser.setUserName(user.getUserName());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setPassword(user.getPassword());

		// セッションに更新したユーザー情報を保存
		session.setAttribute("registeredUser", updatedUser);

		return updatedUser;
	}

	// セッション情報取得
	public accountDto getSessionUser(HttpSession session) {
		// セッションから"registeredUser"属性を取得
		accountDto registeredUser = (accountDto) session.getAttribute("registeredUser");

		// セッションにユーザー情報があるかチェック
		if (registeredUser == null) {
			return null;
		}

		// セッションから取得したユーザー情報を新しいDTOに設定して返す
		accountDto responseUser = new accountDto();
		responseUser.setUserId(registeredUser.getUserId());
		responseUser.setUserName(registeredUser.getUserName());
		responseUser.setEmail(registeredUser.getEmail());
		responseUser.setPassword(registeredUser.getPassword());

		return responseUser;
	}

}