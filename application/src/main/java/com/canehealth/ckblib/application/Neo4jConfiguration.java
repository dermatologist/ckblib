package com.canehealth.ckblib.application;


import java.util.Collection;
import java.util.Collections;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.AbstractReactiveNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableReactiveNeo4jRepositories
@EnableTransactionManagement
// @Profile({ "production" })
public class Neo4jConfiguration extends AbstractReactiveNeo4jConfig {

    @Value("${ckb.graph.neo4j.bolt.url}")
    private String boltUrl;

    @Value("${ckb.graph.neo4j.bolt.user}")
    private String boltUser;

    @Value("${ckb.graph.neo4j.bolt.pass}")
    private String boltPass;

    @Bean
    public Driver driver() {
        return GraphDatabase.driver(boltUrl, AuthTokens.basic(boltUser, boltPass));
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singletonList(DiseaseDisorderMention.class.getPackage().getName());
    }
}
