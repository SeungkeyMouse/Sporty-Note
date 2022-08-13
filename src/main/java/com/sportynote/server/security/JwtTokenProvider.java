package com.sportynote.server.security;

import java.security.Key;
import java.util.Date;

import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.repository.UserBasicRepository;
<<<<<<< HEAD
import com.sportynote.server.util.RedisUtil;
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {
    private final Key secretKey;
    private final UserBasicRepository userBasicRepository;
<<<<<<< HEAD
    private final RedisUtil redisUtil;
    Long accessTokenValidTime = 36000L * 60 * 60;

    public JwtTokenProvider(@Value("${jwtSecretKey}") String secretKey,UserBasicRepository userBasicRepository,RedisUtil redisUtil) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.userBasicRepository= userBasicRepository;
        this.redisUtil=redisUtil;
=======
    Long accessTokenValidTime = 36000L * 60 * 60;

    public JwtTokenProvider(@Value("${jwtSecretKey}") String secretKey,UserBasicRepository userBasicRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.userBasicRepository= userBasicRepository;
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
    }
    /**
     * 유저 고유 ID(UUID)를 받아 AccessToken 발행
     * @return accessToken
     */
    public String createAccessToken(String uuid) {
        Claims claims = Jwts.claims().setSubject("access_token");
<<<<<<< HEAD
        claims.put("userId", uuid);
=======
        claims.put("uid", uuid);
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
        Date currentTime = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
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
<<<<<<< HEAD
        String userId = getTokenToUserId(token);
=======
        String userId = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userId",String.class);
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
        UserBasic userBasic = userBasicRepository.findById(userId);
        UserBasicPrincipal userBasicPrincipal = new UserBasicPrincipal(userBasic);
        return new UsernamePasswordAuthenticationToken(userBasicPrincipal,token, userBasicPrincipal.getAuthorities());
    }

    /**
<<<<<<< HEAD
     * JwtToken에서 유저 ID를 가져오는 함수
     * @return String userId
     * * */
    public String getTokenToUserId(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userId",String.class);
    }

    /**
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
     * 클라이언트의 Token이 유효한지 검증하는 함수
     * @return 토큰이 유효한지 반환 true / false
     */
    public boolean validateToken(String token) {
        try {
            if (token == null) {
                return false;
            }
<<<<<<< HEAD
            if(redisUtil.hasKeyBlackList(token)){
                return false;
            }
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
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
