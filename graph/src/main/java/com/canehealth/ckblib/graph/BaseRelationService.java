package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.BaseRelation;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

@Service
public class BaseRelationService {

    @Autowired
    Driver driver;

    public String getRelationsByCui(String cui) {
        String externalQuery = "MATCH (d {cui: '" + cui + "'}) -[r]- (s) RETURN r";
        D3Map d3Map = new D3Map.Builder(driver).withQuery(externalQuery).build();
        return d3Map.fetch();
    }


}
