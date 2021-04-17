package com.canehealth.ckblib.graph;

import static org.assertj.core.api.Assertions.*;

import com.canehealth.ckblib.graph.CkbConfiguration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.springframework.boot.test.autoconfigure.Neo4jTestHarnessAutoConfiguration;
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
@SpringBootTest(classes = {DiseaseDisorderService.class,
        DiseaseDisorderMention.class,
        CkbTestConfiguration.class})
@EnableAutoConfiguration
@ContextConfiguration(initializers = { NewHarnessTest.Initializer.class })
@ActiveProfiles({ "test" })
class NewHarnessTest {

    private static Neo4j embeddedDatabaseServer;

    @Autowired
    DiseaseDisorderService diseaseDisorderService;

    @Autowired
    private DiseaseDisorderMention diseaseDisorderMention;

    @BeforeAll
    static void initializeNeo4j() { // <.>

        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder().withDisabledServer() // <.>
                // .withFixture(""
                //         + "CREATE (pv:Disease {cui:'C0041834', name:'Psoriasis Vulgaris'})\n"
                //         + "CREATE (lp:Disease {cui:'C0041835', name:'Lichen Planus'})\n"
                //         + "CREATE (pv2:Disease {cui:'C0041836', name:'Pemphigus Vulgaris'})\n")
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

    }
    // end::test-harness-example-option3[]

    @Test
    void shouldRetrieveMovieTitles() {
        diseaseDisorderMention.setCui("C0041834");
        diseaseDisorderMention.setName("Psoriasis Vulgaris");
        diseaseDisorderService.saveDisease(diseaseDisorderMention).block();
        System.out.println(diseaseDisorderService.getDiseaseByCui("C0041834").block().getName());

        // assertThat(diseaseDisorderService.getDiseasesByName("psoriasis").next().block().getName()).hasSize(3).contains("The Matrix");
    }
    // tag::test-harness-example-option3[]
}
// end::test-harness-example-option3[]