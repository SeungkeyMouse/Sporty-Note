package com.sportynote.server;

import com.sportynote.server.config.JasyptConfig;
import com.sportynote.server.mysql.MySQLConnectionTest;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

@Configuration
@SpringBootTest(classes= MySQLConnectionTest.class)
class ServerApplicationTests {


	@Test
	void contextLoads() throws Exception {
		String plainText = "hello";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("password");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);

		String encryptText = encryptor.encrypt(plainText);
		System.out.println(encryptText);
		String decryptText = encryptor.decrypt(encryptText);
		System.out.println(decryptText);
		assertThat(plainText).isEqualTo(decryptText);
	}
}
