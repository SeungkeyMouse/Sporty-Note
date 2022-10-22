package com.sportynote.server.controller;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import java.security.*;


@Controller
public class TestController {

    public static final String TEAM_ID = "9NS2G9S8SM";
    public static final String REDIRECT_URL = "https://api.sportynote.com/auth/code";
    public static final String CLIENT_ID = "com.sportynote.services";
    public static final String KEY_ID = "2RJ9H5FGB8";
    public static final String AUTH_URL = "https://appleid.apple.com";
    public static final String KEY_PATH = "static/apple/AuthKey_QS9UWDPNF3.p8";

    @GetMapping("/test")
    public Integer Test() throws IOException {
        System.out.println(readPrivateKey("static/apple/AuthKey_QS9UWDPNF3.p8"));
        return 1;
    }
    @RequestMapping(value = "/login/getAppleAuthUrl")
    public @ResponseBody String getAppleAuthUrl(HttpServletRequest request) throws Exception {
        String reqUrl =
                AUTH_URL
                        + "/auth/authorize?client_id="
                        + CLIENT_ID
                        + "&redirect_uri="
                        + REDIRECT_URL
                        + "&response_type=code id_token&scope=name email&response_mode=form_post";
        return reqUrl;
    }
//    @RequestMapping(value = "/login/oauth_apple")
//    public String oauth_apple(HttpServletRequest request, @RequestParam(value = "code", required= false) String code
//            , HttpServletResponse response
//            , Model model) throws Exception {
//
//        // 애플로그인 취소시 로그인페이지로 이동
//        if(code == null) {
//            return "/test/test";
//        }
//
//        String client_id = CLIENT_ID;
//        String client_secret = createClientSecret(TEAM_ID, CLIENT_ID, KEY_ID, KEY_PATH, AUTH_URL);
//
//        // 토큰 검증 및 발급
//        String reqUrl = AUTH_URL + "/auth/token";
//        Map<String, String> tokenRequest = new HashMap<>();
//        tokenRequest.put("client_id", client_id);
//        tokenRequest.put("client_secret", client_secret);
//        tokenRequest.put("code", code);
//        tokenRequest.put("grant_type", "authorization_code");
//        String apiResponse = doPost(reqUrl, tokenRequest);
//
//        JSONObject tokenResponse = new JSONObject(apiResponse);
//        JSONObject appleInfo = decodeFromIdToken(tokenResponse.getString("id_token"));
//
//        /**
//
//         TO DO : 리턴받은 appleInfo로
//         , 회원가입처리
//         , 로그인처리
//         , 처리 후 원하는 위치 이동
//         */
//
//
//        // 추후 아래는 삭제
//        model.addAttribute("appleInfo", appleInfo);
//        return "/test/test";
//    }
    public String createClientSecret(String teamId, String clientId, String keyId, String keyPath, String authUrl) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(keyId).build();
        JWTClaimsSet claimsSet = new JWTClaimsSet();
        Date currentTime = new Date();
        claimsSet.setIssuer(teamId);
        claimsSet.setIssueTime(currentTime);
        claimsSet.setExpirationTime(new Date(currentTime.getTime() + 3600000));
        claimsSet.setAudience(authUrl);
        claimsSet.setSubject(clientId);

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(readPrivateKey(keyPath));
        KeyFactory kf = KeyFactory.getInstance("EC");
        try {
            ECPrivateKey ecPrivateKey = (ECPrivateKey) kf.generatePrivate(spec);
            JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey.getS());
            jwt.sign(jwsSigner);
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return jwt.serialize();
}
    private byte[] readPrivateKey(String keyPath) throws IOException {
        Resource resource = new ClassPathResource(keyPath);
        byte[] content = null;

        try(FileReader keyReader = new FileReader(resource.getFile());
            PemReader pemReader = new PemReader(keyReader))
        {
            PemObject pemObject = pemReader.readPemObject();
            content = pemObject.getContent();

        } catch(IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}