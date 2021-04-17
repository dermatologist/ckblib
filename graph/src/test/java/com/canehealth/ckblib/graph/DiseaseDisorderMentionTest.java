package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = DiseaseDisorderMention.class)
public class DiseaseDisorderMentionTest {
    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @Test
    public void testDiseaseDisorderMentionModel() {
        diseaseDisorderMention.setCui("C0041834");
        assertEquals(diseaseDisorderMention.getCui(), "C0041834");
    }

}
