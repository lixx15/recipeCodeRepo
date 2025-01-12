package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.LoginDto;
import jp.co.feeps.service.LoginService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            LoginDto userDto = loginService.loginUser(loginDto.getEmail(), loginDto.getPassword(), session);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/check-session")
    public ResponseEntity<LoginDto> checkSession(HttpSession session) {
        LoginDto loggedInUser = (LoginDto) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        loginService.logoutUser(session);
        return ResponseEntity.ok().build();
    }
}
