package com.sportynote.server.repository.query.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

public class GoogleOauthDto {

    /**
     * 사용자에게 Kakao Oauth 인가코드를 받아 Token을 요청 후 응답에 있는 token을 매핑하기 위한 DTO
     */
    @Data
    @AllArgsConstructor
    public static class GoogleRequestOauthDto {
        private String uid;
        private String email;
        private String name;
        public GoogleRequestOauthDto (){

        }
    }
    /**
     * 사용자에게 Kakao Oauth 인가코드를 받아 Token을 요청 후 응답에 있는 token을 매핑하기 위한 DTO
     */
    @Getter
    public static class GetGoogleOauthTokenResponseDto {
        private String access_token;
    }

    /**
     * 카카오 API로부터 유저 정보를 매핑하기 위한 DTO
     */
    @ToString
    @Getter
    public static class GetGoogleUserInformationResponseDto {
        private String id;
        private String email;
        private String name;
    }

    /**
     * Kakao Oauth Response DTO
     */
    @Getter
    @AllArgsConstructor
    public static class GoogleLoginResponse {
        private boolean success;
        private String accessToken;
    }

}
