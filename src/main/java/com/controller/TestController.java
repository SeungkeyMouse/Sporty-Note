package com.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;

@RestController
public class TestController {
    @Value(value = "${spring.datasource.driver-class-name}")
    private String DRIVER ;

    @Value(value ="${spring.datasource.url}")
    private String URL ;

    @Value(value ="${spring.datasource.username}")
    private String USER ;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @GetMapping("/")
    public int main() throws Exception {
        System.out.println("!");
        System.out.println(DRIVER);
        Class.forName(DRIVER);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println(connection+"연결 성공");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }



}