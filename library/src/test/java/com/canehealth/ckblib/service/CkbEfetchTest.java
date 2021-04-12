package com.canehealth.ckblib.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.canehealth.ckblib.model.BaseQuery;
import com.canehealth.ckblib.model.EsearchResultRoot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class CkbEfetchTest {

    @Autowired
    private BaseQuery baseQuery;

    @Autowired
    private CkbEfetch ckbEfetch;

    @Test
    public void CkbEfetchTestGet() throws InterruptedException {
        ckbEfetch.setBaseQuery(baseQuery);
        TimeUnit.SECONDS.sleep(3);
        ckbEfetch.get();
        TimeUnit.SECONDS.sleep(5);
        System.out.print(ckbEfetch.getResults().get(0));
    }

    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}
