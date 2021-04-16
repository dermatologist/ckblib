package com.canehealth.ckblib.graph.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.service.DiseaseDisorderService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class DiseaseDisorderServiceTest {
    @Autowired
    private DiseaseDisorderService diseaseDisorderService;

    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @Test
    public void testDiseaseDisorderServiceTest() {
        diseaseDisorderMention.setCui("CUI1234");
        assertEquals(diseaseDisorderMention.getCui(), "CUI1234");
        diseaseDisorderService.saveDisease(diseaseDisorderMention);
    }

    //This is required
    @SpringBootApplication
    static class TestConfiguration {
    }
}
