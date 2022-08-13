package com.sportynote.server.controller;
import com.sportynote.server.repository.query.auth.GoogleOauthDto.*;
import com.sportynote.server.repository.query.auth.KakaoOauthDto.*;
import com.sportynote.server.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
<<<<<<< HEAD
@RequestMapping("/auth")
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
public class LoginController {
    AuthService authService;

    public LoginController(AuthService authService){
        this.authService = authService;
    }

    /**
     * 사용자로부터 인가코드를 받아 카카오 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
<<<<<<< HEAD
    @GetMapping("/kakao/callback")
    public KakaoLoginResponse kakaoOauth(@RequestParam("code") String code) {
=======
    @GetMapping("/auth/kakao/callback")
    public KakaoLoginResponse kakaoOauth(@RequestParam("code") String code) {
        System.out.println(code+"CODECODE");
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
        String accessToken = authService.getKakaoOauthToken(code);
        return new KakaoLoginResponse(true, accessToken);
    }

    /**
     * 사용자로부터 인가코드를 받아 구글 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
<<<<<<< HEAD
    @GetMapping("/google/callback")
=======
    @GetMapping("/auth/google/callback")
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
    public GoogleLoginResponse googleOauth(@RequestParam("access_token") String Token) {
        String accessToken = authService.GoogleLogin(Token);
        return new GoogleLoginResponse(true, accessToken);
    }

    /**
     * 사용자로부터 access token을 받아 이를 만료시키는 코드
     */
    @GetMapping("/logout")
    public ResponseEntity<?> jwtLogout(@RequestParam("access_token") String Token) {
<<<<<<< HEAD
        authService.oauthLogout(Token);
=======
        String accessToken = authService.GoogleLogin(Token);
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
        return ResponseEntity.ok(200);
    }

    @GetMapping("/")
    public String Test() {
        return "1";
    }



}
