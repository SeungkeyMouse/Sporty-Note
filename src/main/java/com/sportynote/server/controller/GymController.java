package com.sportynote.server.controller;

import com.sportynote.server.service.GymService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.interfaces.ECPrivateKey;
import java.util.HashMap;
import java.util.Map;

import static com.sportynote.server.controller.TestController.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {
    public static final String TEAM_ID = "9NS2G9S8SM";
    public static final String CLIENT_ID = "com.sportynote.services";
    public static final String KEY_ID = "2RJ9H5FGB8";
    public static final String AUTH_URL = "https://appleid.apple.com";
    public static final String KEY_PATH = "static/apple/AuthKey_QS9UWDPNF3.p8";
    //http://sportynote.com.s3.ap-northeast-2.amazonaws.com/static/apple/AuthKey_QS9UWDPNF3.p8
    TestController testController;
    @RequestMapping(value = "/login/oauth_apple")
    public ResponseEntity<?> oauth_apple(HttpServletRequest request, @RequestParam(value = "code", required= false) String code
            , HttpServletResponse response) throws Exception {
        // 애플로그인 취소시 로그인페이지로 이동
//        if(code == null) {
//            return "/test/test";
//        }

        String client_id = CLIENT_ID;
        String client_secret = testController.createClientSecret(TEAM_ID, CLIENT_ID, KEY_ID, KEY_PATH, AUTH_URL);

        // 토큰 검증 및 발급
        String reqUrl = AUTH_URL + "/auth/token";
        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("client_id", client_id);
        tokenRequest.put("client_secret", client_secret);
        tokenRequest.put("code", code);
        tokenRequest.put("grant_type", "authorization_code");
        String apiResponse = testController.doPost(reqUrl, tokenRequest);

        JSONObject tokenResponse = new JSONObject(apiResponse);

        JSONObject appleInfo=null;
        if (tokenResponse.get("error")==null){
            appleInfo = testController.decodeFromIdToken(tokenResponse.getString("id_token"));
            String appleUniqueNo = appleInfo.getString("sub");
        }
        /**

         TO DO : 리턴받은 appleInfo로
         , 회원가입처리
         , 로그인처리
         , 처리 후 원하는 위치 이동
         */
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(appleInfo);
    }
}
