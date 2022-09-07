package com.sportynote.server.controller;
import com.sportynote.server.repository.query.auth.GoogleOauthDto.*;
import com.sportynote.server.repository.query.auth.KakaoOauthDto.*;
import com.sportynote.server.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService){
        this.authService = authService;
    }

    /**
     * 사용자로부터 인가코드를 받아 카카오 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
    @GetMapping("/kakao/callback")
    public KakaoLoginResponse kakaoOauth(@RequestParam("code") String code) {
        String accessToken = authService.getKakaoOauthToken(code);
        return new KakaoLoginResponse(true, accessToken);
    }


    /**
     * 사용자로부터 인가코드를 받아 구글 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
    @GetMapping("/google/callback")
    public GoogleLoginResponse googleOauth(@RequestParam("code") String code) {
        String accessToken = authService.getGoogleOauthToken(code);
        return new GoogleLoginResponse(true, accessToken);
    }

    /**
     * 사용자로부터 access token을 받아 이를 만료시키는 코드
     */
    @GetMapping("/logout")
    public ResponseEntity<?> jwtLogout(@RequestParam("access_token") String Token) {
        authService.oauthLogout(Token);
        return ResponseEntity.ok(200);
    }

    /**
     * Oauth 서버에서 응답받은 인가코드를 받는 코드
     * */
    @GetMapping("/code")
    public String StringCode (@RequestParam("code") String code) {
        return code;
    }

}
