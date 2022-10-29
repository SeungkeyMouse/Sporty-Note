package com.sportynote.server.service;

import com.sportynote.server.type.SocialType;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.properties.RedisProperties;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.query.auth.GoogleOauthDto.*;
import com.sportynote.server.repository.query.auth.KakaoOauthDto.*;
import com.sportynote.server.repository.repositoryImpl.UserBasicRepositoryImpl;
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.util.RedisUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@Component
@Getter
public class AuthService {
    private final RestTemplate restTemplate;
    private final UserBasicRepository userBasicRepository;
    private final UserBasicRepositoryImpl userBasicRepositoryImpl;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final RedisProperties redisProperties;

    public AuthService(RestTemplate restTemplate, UserBasicRepository userBasicRepository, JwtTokenProvider jwtTokenProvider, UserBasicRepositoryImpl userBasicRepositoryImpl, RedisUtil redisUtil, RedisProperties redisProperties) {
        this.restTemplate = restTemplate;
        this.userBasicRepository=userBasicRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userBasicRepositoryImpl=userBasicRepositoryImpl;
        this.redisUtil=redisUtil;
        this.redisProperties=redisProperties;
    }

    /**
     * 사용자로부터 인가 CODE를 받아서 TOKEN을 요청하는 함수
     */
    public String getKakaoOauthToken(String code)  {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();

        data.add("grant_type", "authorization_code");
        data.add("client_id", KAKAO_OAUTH_API_KEY);
        data.add("redirect_uri", KAKAO_OAUTH_REDIRECT_URI);
        data.add("code", code);

        URI uri = UriComponentsBuilder
                .fromUriString(KAKAO_OAUTH_TOKEN_API_URI)
                .encode()
                .build()
                .toUri();
        ResponseEntity<GetKakaoOauthTokenResponseDto> result = restTemplate.postForEntity(uri, data,
                GetKakaoOauthTokenResponseDto.class);
        if (result.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        String token = result.getBody().getAccess_token();
        return kakaoLogin(token);
    }



    /**
     * 사용자로부터 인가 CODE를 받아서 TOKEN을 요청하는 함수
     */
//    public String getGoogleOauthToken(String code)  {
//        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
//        data.add("grant_type", "authorization_code");
//        data.add("client_id", GOOGLE_OAUTH_CLIENT_ID);
//        data.add("client_secret", GOOGLE_OAUTH_CLIENT_SECRET);
//        data.add("redirect_uri", GOOGLE_OAUTH_REDIRECT_URI);
//        data.add("code", code);
//
//        URI uri = UriComponentsBuilder
//                .fromUriString(GOOGLE_OAUTH_TOKEN_API_URI)
//                .encode()
//                .build()
//                .toUri();
//
//        ResponseEntity<GetGoogleOauthTokenResponseDto> result = restTemplate.postForEntity(uri, data,
//                GetGoogleOauthTokenResponseDto.class);
//
//        if (result.getStatusCode() != HttpStatus.OK) {
//            return null;
//        }
//        String token = result.getBody().getAccess_token();
//        return googleLogin(token);
//    }


    /**
     * Kakao 로그인 처리 후 accessToken 발행하는 함수
     * @param token
     * @return AccessToken
     */
    public String kakaoLogin(String token)  {
        GetKakaoUserInformationResponseDto kakaoUserInformation = getKakaoUserInformation(token);
        String kakaoUserId = Long.toString(kakaoUserInformation.getId());
        UserBasic userBasic;
        if (isAlreadyUser(kakaoUserId,SocialType.KAKAO)) { // 이미 DB에 저장되어있는 카카오 유저라면
            userBasic = userBasicRepository.findByOauthId(kakaoUserId);
        } else { // DB에 저장되어 있지 않은 유저라면 신규 생성 후 토큰 발급
            String userId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
            userBasic = UserBasic.createdUserBasic(kakaoUserInformation.getKakao_account().getEmail(),kakaoUserId,
                    kakaoUserInformation.getKakao_account().getProfile().getNickname(),userId,SocialType.KAKAO);
            userBasicRepository.save(userBasic);
        }
        return jwtTokenProvider.createUserToken(userBasic.getUserId());
    }

    /**
     * token을 받아 Kakao 유저의 정보를 받아오는 함수
     */
    public GetKakaoUserInformationResponseDto getKakaoUserInformation(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<GetKakaoUserInformationResponseDto> response = restTemplate.exchange(
                KAKAO_OAUTH_INFORMATION_API_URI,
                HttpMethod.GET,
                request,
                GetKakaoUserInformationResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }





    /**
     * kakaoUserId를 받아 이미 DB에 등록된 사용자인지 확인하는 함수
     */
    public boolean isAlreadyUser(String oauthId,SocialType socialType) {
        boolean userExists = userBasicRepositoryImpl.existsByOauthIdAndSocialType(oauthId,socialType);
        try {
            if (userExists) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }


    /**
     * token을 받아 Kakao 유저의 정보를 받아오는 함수
     */
    public GetGoogleUserInformationResponseDto getGoogleUserInformation(String token) {
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<GetGoogleUserInformationResponseDto> response = restTemplate.exchange(
                GOOGLE_OAUTH_INFORMATION_API_URI+"?access_token="+token,
                HttpMethod.GET,
                request,
                GetGoogleUserInformationResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }



    /**
     * Google 로그인 처리 후 넘겨받은 accessToken으로 JWT accessToken 발행하는 함수
     * @param googleRequestOauthDto
     * @return AccessToken
     */
    public String googleLogin(GoogleRequestOauthDto googleRequestOauthDto) {
        String GoogleUserId = googleRequestOauthDto.getUid();
        UserBasic userBasic;
        if (isAlreadyUser(GoogleUserId,SocialType.GOOGLE)) { // 이미 DB에 저장되어있는 구글 유저라면
            userBasic = userBasicRepository.findByOauthId(GoogleUserId);
        } else { // DB에 저장되어 있지 않은 유저라면 신규 생성 후 토큰 발급
            String userId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
            userBasic = UserBasic.createdUserBasic(googleRequestOauthDto.getEmail(),GoogleUserId,googleRequestOauthDto.getName(),userId,SocialType.GOOGLE);
            userBasicRepository.save(userBasic);
        }
        return jwtTokenProvider.createUserToken(userBasic.getUserId());
    }
    /**
     * Google 로그인 처리 후 넘겨받은 accessToken으로 JWT accessToken 발행하는 함수
     * @param googleRequestOauthDto
     * @return AccessToken
     */
    public String appleLogin(GoogleRequestOauthDto googleRequestOauthDto) {
        String GoogleUserId = googleRequestOauthDto.getUid();
        UserBasic userBasic;
        if (isAlreadyUser(GoogleUserId,SocialType.APPLE)) { // 이미 DB에 저장되어있는 구글 유저라면
            userBasic = userBasicRepository.findByOauthId(GoogleUserId);
        } else { // DB에 저장되어 있지 않은 유저라면 신규 생성 후 토큰 발급
            String userId = UUID.randomUUID().toString().substring(0,8).toUpperCase();
            userBasic = UserBasic.createdUserBasic(googleRequestOauthDto.getEmail(),GoogleUserId,googleRequestOauthDto.getName(),userId,SocialType.APPLE);
            userBasicRepository.save(userBasic);
        }
        return jwtTokenProvider.createUserToken(userBasic.getUserId());
    }
    /**
     * 유저의 id를 받아 유저의 jwt를 레디스에서 제거하는 함수
     * @param userId
     * @param jwtToken
     * @return
     */
    public Boolean oauthLogout(String userId,String jwtToken){
        if (userId.equals(jwtTokenProvider.getTokenToUserId(jwtToken))){
            return redisUtil.delete(jwtToken);
        }
        return false;
    }

    /**
     * Redis에 저장되어 있는 jwt 여부에 따라 로그인 여부를 파악하는 함수
     * @param jwtToken
     * @return
     */
    public Boolean isLogin(String jwtToken) {
        if (jwtToken == null) {
            return false;
        }
        return redisUtil.hasKey(jwtToken);
    }
    @Value("${KAKAO_OAUTH_API_KEY}")
    private String KAKAO_OAUTH_API_KEY;

    @Value("${KAKAO_OAUTH_REDIRECT_URI}")
    public String KAKAO_OAUTH_REDIRECT_URI;

    @Value("${KAKAO_OAUTH_TOKEN_API_URI}")
    private String KAKAO_OAUTH_TOKEN_API_URI;

    @Value("${KAKAO_OAUTH_INFORMATION_API_URI}")
    private String KAKAO_OAUTH_INFORMATION_API_URI;

    @Value("${GOOGLE_OAUTH_CLIENT_ID}")
    private String GOOGLE_OAUTH_CLIENT_ID;

    @Value("${GOOGLE_OAUTH_CLIENT_SECRET}")
    private String GOOGLE_OAUTH_CLIENT_SECRET;

    @Value("${GOOGLE_OAUTH_REDIRECT_URI}")
    private String GOOGLE_OAUTH_REDIRECT_URI;

    @Value("${GOOGLE_OAUTH_TOKEN_API_URI}")
    private String GOOGLE_OAUTH_TOKEN_API_URI;

    @Value("${GOOGLE_OAUTH_INFORMATION_API_URI}")
    private String GOOGLE_OAUTH_INFORMATION_API_URI;



}
