package com.baby7blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@EnableCaching
@SpringBootApplication
public class Baby7BlogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Baby7BlogServerApplication.class, args);
	}

}
