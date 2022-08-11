package com.sportynote.server.controller;
import com.sportynote.server.repository.query.auth.GoogleOauthDto.*;
import com.sportynote.server.repository.query.auth.KakaoOauthDto.*;
import com.sportynote.server.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    AuthService authService;

    public LoginController(AuthService authService){
        this.authService = authService;
    }

    /**
     * 사용자로부터 인가코드를 받아 카카오 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
    @GetMapping("/auth/kakao/callback")
    public KakaoLoginResponse kakaoOauth(@RequestParam("code") String code) {
        System.out.println(code+"CODECODE");
        String accessToken = authService.getKakaoOauthToken(code);
        return new KakaoLoginResponse(true, accessToken);
    }

    /**
     * 사용자로부터 인가코드를 받아 구글 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
    @GetMapping("/auth/google/callback")
    public GoogleLoginResponse googleOauth(@RequestParam("access_token") String Token) {
        String accessToken = authService.GoogleLogin(Token);
        return new GoogleLoginResponse(true, accessToken);
    }

    /**
     * 사용자로부터 access token을 받아 이를 만료시키는 코드
     */
    @GetMapping("/logout")
    public ResponseEntity<?> jwtLogout(@RequestParam("access_token") String Token) {
        String accessToken = authService.GoogleLogin(Token);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/")
    public String Test() {
        return "1";
    }



}
