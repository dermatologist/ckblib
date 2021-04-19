package com.canehealth.ckblib.library.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.concurrent.TimeUnit;
import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.model.EsearchResultRoot;
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

    //Disabled to save time
    // @Test
    // public void CkbEsearchTestGet() throws InterruptedException {
    //     ckbEsearch.setBaseQuery(baseQuery);
    //     ckbEsearch.get();
    //     TimeUnit.SECONDS.sleep(1);
    //     EsearchResultRoot esearchResultRoot = ckbEsearch.getResults().get(0);
    //     System.out.print(esearchResultRoot.esearchresult.count);
    //     assertNotEquals(esearchResultRoot.esearchresult.count, "0");
    //     System.out.print(esearchResultRoot.esearchresult.ids());
    //     assertNotEquals(esearchResultRoot.esearchresult.ids(), "0");
    // }

    @Test
    public void CkbEsearchTestGetMono() throws InterruptedException {
        ckbEsearch.setBaseQuery(baseQuery);
        EsearchResultRoot esearchResultRoot = ckbEsearch.get().block();
        System.out.print(esearchResultRoot.esearchresult.count);
        assertNotEquals(esearchResultRoot.esearchresult.count, "0");
    }
    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}
