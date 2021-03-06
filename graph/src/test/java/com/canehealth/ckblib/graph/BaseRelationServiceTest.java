package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.canehealth.ckblib.graph.model.BaseRelation;
import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.model.SignSymptomMention;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {
        BaseRelationService.class,
        BaseRelation.class, DiseaseDisorderService.class, DiseaseDisorderMention.class, SignSymptomService.class,
        SignSymptomMention.class, Neo4jTestConfiguration.class })
@EnableAutoConfiguration
@ComponentScan({ "com.canehealth.ckblib.graph" })
@ContextConfiguration(initializers = {
        BaseRelationServiceTest.Initializer.class })
@ActiveProfiles({ "test" })
class BaseRelationServiceTest {

    private static Neo4j embeddedDatabaseServer;

    @Autowired
    SignSymptomService signSymptomService;

    @Autowired
    private SignSymptomMention signSymptomMention;

    @Autowired
    DiseaseDisorderService diseaseDisorderService;

    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @Autowired
    BaseRelationService baseRelationService;

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
    void shouldRetrieveDiseases() {
        diseaseDisorderMention.setCui("C0041834");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        try {
            // Should fail if already created
            diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
            signSymptomMention.setCui("C1041834");
            signSymptomMention.setName("Pruritus");
            signSymptomService.saveSymptom(signSymptomMention).block();
            signSymptomMention.setCui("C1041835");
            signSymptomMention.setName("Erythema");
            signSymptomService.saveSymptom(signSymptomMention).block();

        } catch (Exception e) {

        }
        assertEquals(signSymptomService.addRelation("C0041834", "C1041834", 0, 0, 0).block().getName(), "Pruritus");
        assertEquals(signSymptomService.addRelation("C0041834", "C1041835", 0, 0, 0).block().getName(), "Erythema");
        assertFalse(baseRelationService.getRelationsByCui("C0041834").length() < 10);
        String before = baseRelationService.getRelationsByCui("C0041834");
        baseRelationService.addAttributes("C0041834", "C1041834",0, 1, 1).block();
        String after = baseRelationService.getRelationsByCui("C0041834");
        assertNotEquals(before, after);

        String lift = baseRelationService.getLift("C0041834");
        assertFalse(lift.length()<10);
    }

}
