package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.MypageDto;
import jp.co.feeps.DTO.accountDto;
import jp.co.feeps.service.MypageService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MypageController {

    @Autowired
    private MypageService mypageService;
	
	
	// マイページ情報の取得
    @GetMapping("/mypage")
    public ResponseEntity<MypageDto> getMypage(HttpSession session) {
    	
    	accountDto aDto = new accountDto();
    	
    	aDto.setEmail("user1@example.com");
    	aDto.setUserId(1);
    	aDto.setUserName("user1");
    	aDto.setPassword("pass1");
    	
//    	// debug用ユーザー
//    	session.setAttribute("registeredUser", aDto);
//    	
//    	// sessionにユーザー情報がなければ404を返す
//		accountDto sessionUser = accountService.getSessionUser(session);
//		if (sessionUser == null) {
//			return ResponseEntity.notFound().build();
//		}
		
        MypageDto mypageDto = mypageService.getUserMypage(aDto);
        
        return ResponseEntity.ok(mypageDto);
    }
}
