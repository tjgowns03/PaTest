package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class TestApplicationTests {

	//비밀번호
	@Test
	void contextLoads() {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
	}

}
