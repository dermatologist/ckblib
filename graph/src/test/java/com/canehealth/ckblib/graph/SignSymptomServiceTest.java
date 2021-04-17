package com.canehealth.ckblib.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.model.SignSymptomMention;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
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

/**
 * This variant uses a custom {@link ApplicationContextInitializer} that
 * modifies Springs configuration properties with the help of
 * {@link TestPropertyValues}. Thus, the autoconfiguration of the driver is kept
 * and all other things are as you'd expect in production.
 * <p>
 * If you don't like that setup, look at {@link MoviesServiceAlt1Test}. Here, we
 * expose the embedded server as a Spring Bean and don't do the manual
 * connection setting.
 */
// tag::test-harness-example-option3[]
@SpringBootTest(classes = {
        DiseaseDisorderService.class, DiseaseDisorderMention.class, SignSymptomService.class, SignSymptomMention.class, Neo4jTestConfiguration.class })
@EnableAutoConfiguration
@ContextConfiguration(initializers = { SignSymptomServiceTest.Initializer.class })
@ActiveProfiles({ "test" })
class SignSymptomServiceTest {

    private static Neo4j embeddedDatabaseServer;

    @Autowired
    SignSymptomService signSymptomService;

    @Autowired
    private SignSymptomMention signSymptomMention;

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
    void testSomethingWithTheDriver(@Autowired Driver driver) {
        // Intentionally try to create duplicate
        diseaseDisorderMention.setCui("C0041834");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        diseaseDisorderMention.setVersion(1L);
        diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
    }
    // end::test-harness-example-option3[]

    @Test
    void shouldRetrieveDiseases() {
        signSymptomMention.setCui("C1041834");
        signSymptomMention.setName("Pruritus");
        signSymptomMention.setVersion(1L);
        try {
            // Should fail if already created
            signSymptomService.saveSymptom(signSymptomMention).block();
        } catch (Exception e) {

        }

        signSymptomService.addRelation("C0041834", "C1041834");

        // System.out.println(signSymptomService.getDiseaseByCui("C0041834").block().getName());
        assertEquals(signSymptomService.getSymptomByCui("C1041834").block().getName(), "Pruritus");
        // assertThat(signSymptomService.getDiseasesByName("psoriasis").next().block().getName()).hasSize(3).contains("The
        // Matrix");
    }
    // tag::test-harness-example-option3[]

}
// end::test-harness-example-option3[]