package com.canehealth.ckblib.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest("service.message=Hello")
public class MyServiceTest {

	@Autowired
	private MyService myService;

	@Test
	public void contextLoads() {
		assertThat(myService.message()).isNotNull();
	}

	@SpringBootApplication
	@Configuration
	@ComponentScan(basePackages = "com.canehealth.ckblib")
	static class TestConfiguration {
	}

}
