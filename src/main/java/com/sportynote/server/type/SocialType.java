package com.sportynote.server.type;

public enum SocialType {
    GOOGLE("google"), KAKAO("kakao"), APPLE("apple");


    private final String ROLE_PREFIX = "ROLE_";
    private final String name;

    SocialType(String name) {
        this.name = name;
    }

    //아직 안쓰는 기능 권한 및 해당 계정이 맞는가
    public String getRoleType() {
        return ROLE_PREFIX + name.toUpperCase();
    }

    public String getValue() {
        return name;
    }

    public boolean isEquals(String authority) {
        return this.name.equals(authority);
    }
}