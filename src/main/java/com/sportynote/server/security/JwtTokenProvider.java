package com.sportynote.server.security;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.security.user.UserAccount;
import com.sportynote.server.util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {
    private final Key secretKey;
    private final UserBasicRepository userBasicRepository;
    private final RedisUtil redisUtil;
    Long accessTokenValidTime = 36000L * 60 * 60 * 60; //1.5일 * 마지막 60
    public JwtTokenProvider(@Value("${jwtSecretKey}") String secretKey,UserBasicRepository userBasicRepository,RedisUtil redisUtil) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.userBasicRepository = userBasicRepository;
        this.redisUtil = redisUtil;
    }

    /**
     * 유저의 고유번호를 받아 accesstoken을 만들고
     * @param uuid
     * @return
     */

    public String createUserToken(String uuid) {
        String token = createAccessToken(uuid);
        createRedisToken(uuid,token);
        return token;
    }

    /**
     * 유저 고유 ID(UUID)를 받아 AccessToken 발행
     * @return accessToken
     */
    public String createAccessToken(String uuid) {
        Claims claims = Jwts.claims().setSubject("access_token");
        claims.put("userId", uuid);
        Date currentTime = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 유저 고유id와 accessToken을 받아 유저정보를 Redis에 저장하는 함수.
     * @param accessToken
     * @return
     */
    public void createRedisToken(String uuid, String accessToken) {
        redisUtil.set(accessToken, getLoginInfo(uuid), accessTokenValidTime/60); //minutes
    }

    public String getLoginInfo(String uuid) {
        Date currentTime = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy MM dd HH:mm");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",uuid);
        jsonObject.put("IAT",date.format(currentTime));
        jsonObject.put("EXP",date.format(currentTime.getTime()+accessTokenValidTime));
        return String.valueOf(jsonObject);
    }



    /**
     * Http Header에서 토큰을 추출
     * @return accessToken
     */
    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * 유저정보를 불러와 인증 객체 반환해주는 함수
     * @return Spring Security 인증 객체
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String userId = getTokenToUserId(token);
        UserBasic userBasic = userBasicRepository.findById(userId);
        UserBasicPrincipal userBasicPrincipal = new UserBasicPrincipal(userBasic);
//        //유저 권한
//        System.out.println(userBasicPrincipal.getAuthorities().iterator().next().getAuthority());
//        //유저 네임
//        System.out.println(userBasicPrincipal.getUsername());
        return new UsernamePasswordAuthenticationToken(new UserAccount(userBasicPrincipal),token, userBasicPrincipal.getAuthorities());
    }

    public UsernamePasswordAuthenticationToken getTestAuthentication() {
        String userId = "be1023ce";//승기 인증코드
        UserBasic userBasic = userBasicRepository.findById(userId);
        UserBasicPrincipal userBasicPrincipal = new UserBasicPrincipal(userBasic);
        return new UsernamePasswordAuthenticationToken(new UserAccount(userBasicPrincipal), "credentials", userBasicPrincipal.getAuthorities());
    }

    /**
     * JwtToken에서 유저 ID를 가져오는 함수
     * @return String userId
     * * */
    public String getTokenToUserId(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userId",String.class);
    }

    /**
     * 클라이언트의 Token이 유효한지 검증하는 함수
     * @return 토큰이 유효한지 반환 true / false
     */
    public boolean validateToken(String token) {
        try {
            if (token == null) {
                return false;
            }
            if(!redisUtil.hasKey(token)){
                return false;
            }
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
                | IllegalArgumentException e) {
            return false;
        }
    }
//
//    /**
//     * 토큰 만료기한 가져오기
//     */
//    public Date getExpirationDate(String token) {
//        Claims claims = getAllClaims(token);
//        return claims.getExpiration();
//    }
//
//    /**
//     * 토큰이 만료되었는지
//     */
//    private boolean isTokenExpired(String token) {
//        return getExpirationDate(token).before(new Date());
//    }

}
