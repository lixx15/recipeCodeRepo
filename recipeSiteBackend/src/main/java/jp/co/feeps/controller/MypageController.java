package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.DTO.AccountDto;
import jp.co.feeps.DTO.MypageDto;
import jp.co.feeps.service.MypageService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MypageController {

    @Autowired
    private MypageService mypageService;	
	
	// マイページ情報の取得
    @GetMapping("/mypage")
    public ResponseEntity<MypageDto> getMypage(HttpSession session) {	   	
	   	// sessionからログインユーザー情報の取得	
    	Object sessionUser = session.getAttribute("registeredUser");
    	
    	// sessionにユーザー情報がなければ403を返す	
	    if (sessionUser == null) {
	    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
	    
	    //　明示的にDtoに変換
	    AccountDto user = (AccountDto)sessionUser;
	    MypageDto mypageDto = mypageService.getUserMypage(user);
	    return ResponseEntity.ok(mypageDto);
    }
}
