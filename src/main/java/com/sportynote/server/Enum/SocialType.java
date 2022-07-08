package com.sportynote.server.Enum;

public enum     SocialType {
    GOOGLE("google"), KAKAO("kakao");

    private final String ROLE_PREFIX = "ROLE_";
    private final String name;

    SocialType(String name) {
        this.name = name;
    }

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