package com.sportynote.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class TestController {

    public static final String TEAM_ID = "9NS2G9S8SM";
    public static final String REDIRECT_URL = "https://sportynote.com/auth/code";
    public static final String CLIENT_ID = "com.sportynote.services";
    public static final String KEY_ID = "2RJ9H5FGB8";
    public static final String AUTH_URL = "https://appleid.apple.com";
    public static final String KEY_PATH = "static/apple/AuthKey_QS9UWDPNF3.p8";

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
}
