package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;


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
