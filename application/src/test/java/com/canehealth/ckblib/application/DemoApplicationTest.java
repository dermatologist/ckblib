package com.canehealth.ckblib.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.canehealth.ckblib.library.service.MyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;

@SpringBootTest
public class DemoApplicationTest {

	@Autowired
	private MyService myService;

	@Test
	public void contextLoads() {
		assertThat(myService.message()).isNotNull();
	}

	//Use this while testing locally
	@Test
	public void demoTest() {
		String[] args = { "Erythema Multiforme"};
		DemoApplication.main(args);
	}

	// This is required
	// @SpringBootApplication
	// @Configuration
	// //@EnableReactiveNeo4jRepositories(basePackages = "com.canehealth.ckblib.graph")
	// @ComponentScan(basePackages = "com.canehealth.ckblib")
	// static class TestConfiguration {
	// }

}
