package com.canehealth.ckblib.graph;

import java.util.Collection;
import java.util.Collections;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractReactiveNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableReactiveNeo4jRepositories
@EnableTransactionManagement
@Profile({ "test" })
public class CkbTestConfiguration extends AbstractReactiveNeo4jConfig {

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singletonList(DiseaseDisorderMention.class.getPackage().getName());
    }

    @Override
    public Driver driver() {
        // TODO Auto-generated method stub
        return null;
    }
}
