package com.sportynote.server.repository.query.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class KakaoOauthDto {

    /**
     * 사용자에게 Kakao Oauth 인가코드를 받아 Token을 요청 후 응답에 있는 token을 매핑하기 위한 DTO
     */
    @Getter
    public static class GetKakaoOauthTokenResponseDto {
        private String access_token;
    }

    /**
     * 카카오 API로부터 유저 정보를 매핑하기 위한 DTO
     */
    @ToString
    @Getter
    public static class GetKakaoUserInformationResponseDto {
        private Long id;
        private kakao_account kakao_account;
    }

    /**
     * GetKakaoUserInformationResponseDto에서 사용되는 class
     */
    @Getter
    public static class kakao_account {
        private String email;
        private String name;
        private profile profile;
    }

    /**
     * GetKakaoUserInformationResponseDto에서 사용되는 class
     */
    @Getter
    @Setter
    public static class profile {
        private String nickname;
    }

    /**
     * Kakao Oauth Response DTO
     */
    @Getter
    @AllArgsConstructor
    public static class KakaoLoginResponse {
        private boolean success;
        private String accessToken;
    }
}