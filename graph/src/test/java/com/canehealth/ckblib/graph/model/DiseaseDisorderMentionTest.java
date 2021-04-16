package com.canehealth.ckblib.graph.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;

@SpringBootTest
public class DiseaseDisorderMentionTest {
    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @Test
    public void testDiseaseDisorderMentionModel() {
        diseaseDisorderMention.setCui("CUI1234");
        assertEquals(diseaseDisorderMention.getCui(), "CUI1234");
    }

    // This is required
    @SpringBootApplication
    static class TestConfiguration {
    }
}
