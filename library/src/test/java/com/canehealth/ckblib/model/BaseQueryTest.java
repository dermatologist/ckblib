package com.canehealth.ckblib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class BaseQueryTest {

    @Autowired
    private BaseQuery baseQuery;

    @Test
    public void testBaseQuery() {
        assertEquals(baseQuery.getDb(), "pubmed");
        assertEquals(baseQuery.getQuery(), "?retmode=json&db=pubmed&term=Eapen+BR&retmax=10");
    }

    // This is required
    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }
}
