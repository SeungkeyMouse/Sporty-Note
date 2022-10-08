package com.sportynote.server.controller;
import com.sportynote.server.repository.query.auth.GoogleOauthDto.*;
import com.sportynote.server.repository.query.auth.KakaoOauthDto.*;
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.AuthService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    public LoginController(AuthService authService, JwtTokenProvider jwtTokenProvider){
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 사용자로부터 인가코드를 받아 카카오 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
    @GetMapping("/kakao/callback")
    public KakaoLoginResponse kakaoOauth(@RequestParam("code") String code) throws JSONException {
        String accessToken = authService.getKakaoOauthToken(code);
        return new KakaoLoginResponse(true, accessToken);
    }


    /**
     * 사용자로부터 인가코드를 받아 구글 서버로부터 유저 정보를 받고 AccessToken 발행
     * @return 로그인 성공 유무 및 accessToken(JWT)
     */
    @GetMapping("/google/callback")
    public GoogleLoginResponse googleOauth(@RequestParam("code") String code) throws JSONException {
        String accessToken = authService.getGoogleOauthToken(code);
        return new GoogleLoginResponse(true, accessToken);
    }

    /**
     * 사용자로부터 access token을 받아 이를 만료시키는 코드
     */
    @GetMapping("/logout")
    public ResponseEntity<?> jwtLogout(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal,HttpServletRequest request) {
        String jwtToken = jwtTokenProvider.getTokenFromHeader(request);
        return ResponseEntity.status(200).body(authService.oauthLogout(userBasicPrincipal.getUserId(),jwtToken));
    }

    /**
     * Oauth 서버에서 응답받은 인가코드를 받는 코드
     * */
    @GetMapping("/code")
    public String StringCode (@RequestParam("code") String code) {
        return code;
    }

}
