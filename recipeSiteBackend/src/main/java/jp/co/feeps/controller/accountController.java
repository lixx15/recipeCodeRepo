package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.AccountDto;
import jp.co.feeps.service.AccountService;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AccountController {
	@Autowired
	private AccountService accountService;

	// アカウント登録処理
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody AccountDto accountDto, HttpSession session) {
		accountService.registerUser(accountDto, session);
		return ResponseEntity.ok().build();
	}

	// アカウント更新処理
	@PostMapping("/update")
	public ResponseEntity<Void> update(@RequestBody AccountDto accountDto, HttpSession session) {
		accountService.updateUser(accountDto, session);
		return ResponseEntity.ok().build();
	}

	// セッション取得処理
	@GetMapping("/session")
	public ResponseEntity<AccountDto> getSessionUser(HttpSession session) {
		AccountDto sessionUser = accountService.getSessionUser(session);
		return ResponseEntity.ok(sessionUser);
	}
	
    @PostMapping("/login")
    public ResponseEntity<AccountDto> login(@RequestBody AccountDto loginDto, HttpSession session) {
        try {
        	AccountDto userDto = accountService.loginUser(loginDto.getEmail(), loginDto.getPassword(), session);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
//    // TO DO セッションの取得チェック
//    @GetMapping("/check-session")
//    public ResponseEntity<AccountDto> checkSession(HttpSession session) {
//    	AccountDto loggedInUser = (AccountDto) session.getAttribute("loggedInUser");
//        if (loggedInUser != null) {
//            return ResponseEntity.ok(loggedInUser);
//        } else {
//            return ResponseEntity.status(401).body(null);
//        }
//    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
    	accountService.logoutUser(session);
        return ResponseEntity.ok().build();
    }
}