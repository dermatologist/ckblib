package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.model.AnatomicalSiteMention;

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

@SpringBootTest(classes = { DiseaseDisorderService.class, DiseaseDisorderMention.class, AnatomicalSiteService.class,
        AnatomicalSiteMention.class, Neo4jTestConfiguration.class })
@EnableAutoConfiguration
@ContextConfiguration(initializers = { AnatomicalSiteServiceTest.Initializer.class })
@ActiveProfiles({ "test" })
class AnatomicalSiteServiceTest {

    private static Neo4j embeddedDatabaseServer;

    @Autowired
    AnatomicalSiteService anatomicalSiteService;

    @Autowired
    private AnatomicalSiteMention anatomicalSiteMention;

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
    void shouldRetrieveAnatomies() {
        diseaseDisorderMention.setCui("C0041834");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        anatomicalSiteMention.setCui("C2041834");
        anatomicalSiteMention.setName("Face");
        try {
            // Should fail if already created
            diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
            anatomicalSiteService.saveSymptom(anatomicalSiteMention).block();

        } catch (Exception e) {

        }
        assertEquals(anatomicalSiteService.addRelation("C0041834", "C2041834", 0, 0, 0).block().getName(), "Face");

        System.out.println(anatomicalSiteService.forD3("C2041834").toString());
    }

}
