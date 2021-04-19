package com.canehealth.ckblib.library.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class CkbEfetchTest {

    @Autowired
    private CkbEfetch ckbEfetch;

    @Test
    public void getAbstractFromXml() throws IOException {
        String xmlString =  new String(Files.readAllBytes(Paths.get("src/main/resources/efetch.xml")));
        List<String> abstracts = ckbEfetch.getPathFromString("//Abstract", xmlString);
        assertTrue(abstracts.get(0).length() > 100);
    }

    // Too many requests

    // @Test
    // public void CkbEfetchTestGet() throws InterruptedException {
    //     baseQuery.setTerm("Lichen Planus");
    //     //baseQuery.setRetmax(3);
    //     TimeUnit.SECONDS.sleep(2);
    //     ckbEfetch.setBaseQuery(baseQuery);
    //     TimeUnit.SECONDS.sleep(2);
    //     ckbEfetch.get();
    //     TimeUnit.SECONDS.sleep(3);
    //     List<String> abstracts = ckbEfetch.getPath("//Abstract");
    //     // System.out.println(abstracts.get(0));
    //     assertTrue(abstracts.get(0).length() > 100);
    // }

    // Will lead to too many requests

    // @Test
    // public void CkbEfetchTestGetMono() throws InterruptedException {
    //     baseQuery.setTerm("Lichen Planus");
    //     ckbEfetch.setBaseQuery(baseQuery);
    //     TimeUnit.SECONDS.sleep(1);
    //     ckbEfetch.get().block();
    //     List<String> abstracts = ckbEfetch.getPath("//Abstract");
    //     assertTrue(abstracts.get(0).length() > 100);
    // }

    /*
            The test below is disabled as it leads to too many requests to PubMed
    */

    // @Test
    // public void CkbEfetchTestGetChain() throws InterruptedException {
    //     baseQuery.setTerm("Lichen Planus");
    //     String s = ckbEfetch.getChain(baseQuery).block();
    //     List<String> abstracts = ckbEfetch.getPathFromString("//Abstract", s);
    //     System.out.println(abstracts.get(0));
    //     assertTrue(abstracts.get(0).length() > 100);
    // }
    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}
