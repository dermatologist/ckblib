package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.model.MedicationMention;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = { D3MapBuilder.class,
        DiseaseDisorderService.class, DiseaseDisorderMention.class, MedicationService.class,
        MedicationMention.class, Neo4jTestConfiguration.class })
@EnableAutoConfiguration
@ContextConfiguration(initializers = { MedicationServiceTest.Initializer.class })
@ActiveProfiles({ "test" })
class MedicationServiceTest {

    private static Neo4j embeddedDatabaseServer;

    @Autowired
    MedicationService medicationService;

    @Autowired
    private MedicationMention medicationMention;

    @Autowired
    DiseaseDisorderService diseaseDisorderService;

    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @BeforeAll
    static void initializeNeo4j() { // <.>

        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder().withDisabledServer() // <.>
                .build();

    }

    @AfterAll
    static void closeNeo4j() { // <.>
        embeddedDatabaseServer.close();
    }

    // <.>
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("org.neo4j.driver.uri=" + embeddedDatabaseServer.boltURI().toString(),
                            "org.neo4j.driver.authentication.password=")
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void shouldRetrieveMedications() {
        diseaseDisorderMention.setCui("C0041834");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        medicationMention.setCui("C3041834");
        medicationMention.setName("Paracetamol");
        medicationService.saveSymptom(medicationMention).block();
        try {
            // Should fail if already created
            diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
            medicationService.saveSymptom(medicationMention).block();

        } catch (Exception e) {

        }
        assertEquals(medicationService.addRelation("C0041834", "C3041834", 0, 0, 0).block().getName(), "Paracetamol");

        System.out.println(medicationService.forD3("C3041834").toString());
    }

}
