package com.canehealth.ckblib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseQueryTest {

    @Autowired
    private BaseQuery baseQuery;

    @Test
    public void testBaseQuery() {
        assertEquals(baseQuery.getDb(), "pubmed");
        assertEquals(baseQuery.getQuery(), "?db=pubmed&term=Eapen+BR&retmax=10");
    }

    // This is required
    @SpringBootApplication
    static class TestConfiguration {
    }
}
