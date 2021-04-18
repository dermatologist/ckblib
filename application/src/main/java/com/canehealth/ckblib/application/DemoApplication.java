package com.canehealth.ckblib.application;

import com.canehealth.ckblib.library.service.MyService;

import java.util.concurrent.TimeUnit;

import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.service.CkbEfetch;
import com.canehealth.ckblib.qtakes.service.QtakesService;
import com.canehealth.ckblib.qtakes.model.QtakesRoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.canehealth.ckblib")
public class DemoApplication implements CommandLineRunner {

	private final MyService myService;
	private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);
	@Autowired
	private BaseQuery baseQuery;

	@Autowired
	private CkbEfetch ckbEfetch;

	@Autowired
	private QtakesService qtakesService;

	public DemoApplication (MyService myService) {
		this.myService = myService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		LOG.info("EXECUTING : " + myService.message());
		baseQuery.setTerm("Erythema Multiforme");
		ckbEfetch.setBaseQuery(baseQuery);
		TimeUnit.SECONDS.sleep(3);
		ckbEfetch.get();
		TimeUnit.SECONDS.sleep(5);
		String myPublications = ckbEfetch.getPath("//Abstract").get(0);
		String myPMID = ckbEfetch.getPath("//PMID").get(0);
		System.out.print(myPublications);
		System.out.print(myPMID);

		qtakesService.setQtakesUrl("http://127.0.0.1:8093");
		qtakesService.post(myPublications);
		TimeUnit.SECONDS.sleep(3);
		QtakesRoot r = qtakesService.getQtakesResults();
		System.out.print(r.toString());

	}

}
