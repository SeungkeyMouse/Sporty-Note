package com.sportynote.server;

import com.sportynote.server.mysql.MySQLConnectionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
@Configuration
@SpringBootTest(classes= MySQLConnectionTest.class)
class ServerApplicationTests {
	@Value(value = "${spring.datasource.driver-class-TEST}")
	private String DRIVER;

	@Value(value ="${spring.datasource.url}")
	private String URL ;

	@Value(value ="${spring.datasource.username}")
	private String USER ;

	@Value(value ="${spring.datasource.password}")
	private String PASSWORD ;

	@Test
	void contextLoads() throws Exception{
			System.out.println(DRIVER);
			Class.forName(DRIVER);
			try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
				System.out.println(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
