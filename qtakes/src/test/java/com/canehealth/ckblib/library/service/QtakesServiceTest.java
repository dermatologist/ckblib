package com.canehealth.ckblib.library.service;

import java.util.concurrent.TimeUnit;
import com.canehealth.ckblib.qtakes.model.QtakesRoot;
import com.canehealth.ckblib.qtakes.service.QtakesService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class QtakesServiceTest {

    @Autowired
    private QtakesService qtakesService;

    @Test
    public void QtakesServiceTestGet() throws InterruptedException {
        qtakesService.setQtakesUrl("http://127.0.0.1:8093");
        qtakesService.post(
                "Lichen planus presented with pruritus and erythema on the face treated with oral paracetamol and blepharoplasty in the morning");
        TimeUnit.SECONDS.sleep(3);
        String qtakesRoot = qtakesService.getResults().get(0);
        System.out.print(qtakesRoot);
    }

    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}
