package com.baby7blog.modules;

import com.baby7blog.modules.blog.common.CommonConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class Baby7BlogServerApplicationTests {

	@Test
	void contextLoads() {
	}
	public static void main(String[] args) {
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bcryptPasswordEncoder.encode("123456" + CommonConstants.SALT));
	}
}
