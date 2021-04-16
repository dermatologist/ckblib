package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class DiseaseDisorderServiceTest {
    @Autowired
    private DiseaseDisorderService diseaseDisorderService;

    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @Test
    public void testDiseaseDisorderServiceTest() {
        diseaseDisorderMention.setCui("CUI1234");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        assertEquals(diseaseDisorderMention.getCui(), "CUI1234");
        // diseaseDisorderService.saveDisease(diseaseDisorderMention);
        System.out.println(diseaseDisorderService.saveDisease(diseaseDisorderMention).block());
    }

    //This is required
    @SpringBootApplication
    static class TestConfiguration {
    }
}
