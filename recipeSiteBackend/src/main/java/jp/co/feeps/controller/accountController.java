package jp.co.feeps.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jp.co.feeps.DTO.accountDto;
import jp.co.feeps.service.AccountService;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class accountController {
	@Autowired
	private AccountService accountService;

	// アカウント登録処理
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody accountDto accountDto, HttpSession session) {
		accountService.registerUser(accountDto, session);
		return ResponseEntity.ok().build();
	}

	// アカウント更新処理
	@PostMapping("/update")
	public ResponseEntity<Void> update(@RequestBody accountDto accountDto, HttpSession session) {
		accountService.updateUser(accountDto, session);
		return ResponseEntity.ok().build();
	}

	// セッション取得処理
	@GetMapping("/session")
	public ResponseEntity<accountDto> getSessionUser(HttpSession session) {
		accountDto sessionUser = accountService.getSessionUser(session);
		return ResponseEntity.ok(sessionUser);
	}
}