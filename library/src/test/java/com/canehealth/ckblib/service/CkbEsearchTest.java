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
public class CkbEsearchTest {

    @Autowired
    private BaseQuery baseQuery;

    @Autowired
    private CkbEsearch ckbEsearch;

    List<EsearchResultRoot> results = new ArrayList<EsearchResultRoot>();

    @BeforeAll
    public static void beforeAll() {

    }

    @Test
    public void CkbEsearchTestGet() throws InterruptedException {
        ckbEsearch.setBaseQuery(baseQuery);
        ckbEsearch.get().subscribe(results::add);
        TimeUnit.SECONDS.sleep(3);
        EsearchResultRoot esearchResultRoot = results.get(0);
        System.out.print(esearchResultRoot.esearchresult.count);
        assertNotEquals(esearchResultRoot.esearchresult.count, "0");
        assertNotNull(results);
    }

    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}
