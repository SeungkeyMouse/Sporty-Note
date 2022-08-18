package com.sportynote.server.repository.query.auth;

import lombok.Getter;

public class AppleOauthDto {
    @Getter
    public static class GetAppleOauthTokenResponseDto {
        private String access_token;
    }
}
