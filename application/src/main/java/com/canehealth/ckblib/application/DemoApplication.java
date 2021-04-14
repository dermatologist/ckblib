package com.canehealth.ckblib.application;

import com.canehealth.ckblib.library.service.MyService;
import com.canehealth.ckblib.library.util.CkblibConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.canehealth.ckblib")
public class DemoApplication implements CommandLineRunner {

	private final MyService myService;
	private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	public DemoApplication (MyService myService) {
		this.myService = myService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("EXECUTING : " + myService.message());

		String _function = "";
		String _source = "";
		String _destination = "";
		try {
			if (args[0].toLowerCase().equals("help")) {
				System.out.println(CkblibConstants.HELP_STRING);
			} else {
				for (int i = 0; i < args.length; ++i) {
					LOG.debug("args[{}]: {}", i, args[i]);
					_function = args[0];
					_source = args[1];
					_destination = args[2];
				}
			}

			if (_function.equals("tofhirbundle")) {
				try {

				} catch (Exception e) {
					System.out.println(CkblibConstants.HELP_STRING);
				}
			} else if (_function.equals("tofhirserver")) {
				System.out.println("To FHIR SERVER not implemented yet");
			} else if (_function.equals("toomop")) {

			} else if (_function.toLowerCase().equals("help")) {
				System.out.println(CkblibConstants.HELP_STRING);
			}
		} catch (Exception e) {
			System.out.println(CkblibConstants.HELP_STRING);
		}
	}

}
