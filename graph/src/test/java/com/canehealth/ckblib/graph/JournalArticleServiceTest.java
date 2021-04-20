package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.model.JournalArticle;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = { D3Map.class,
        DiseaseDisorderService.class, DiseaseDisorderMention.class, SignSymptomService.class,
        SignSymptomMention.class,
        JournalArticle.class,
        JournalArticleService.class, Neo4jTestConfiguration.class })
@EnableAutoConfiguration
@ContextConfiguration(initializers = {
        JournalArticleServiceTest.Initializer.class })
@ActiveProfiles({ "test" })
class JournalArticleServiceTest {

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
    JournalArticleService journalArticleService;

    @Autowired
    private JournalArticle journalArticle;

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
    void shouldAddDiseaseEvidence() {
        diseaseDisorderMention.setCui("C0041834");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        signSymptomMention.setCui("C1041834");
        signSymptomMention.setName("Pruritus");
        journalArticle.setPmid("PMID123");
        journalArticle.setName("Journal Article");
        try {
            // Should fail if already created
            diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
            signSymptomService.saveSymptom(signSymptomMention).block();
            journalArticleService.saveArticle(journalArticle).block();

        } catch (Exception e) {

        }
        //System.out.println(journalArticleService.addEvidence("C0041834" , "PMID123").block());
        assertNotNull(journalArticleService.addEvidence("C0041834", "PMID123").block());
        assertNotNull(journalArticleService.addEvidence("C1041834", "PMID123").block());
        //assertEquals(signSymptomService.addRelation("C0041834", "C1041834", 0, 0, 0).block().getName(), "Pruritus");
    }

}
