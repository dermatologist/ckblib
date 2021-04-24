package com.canehealth.ckblib.graph;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canehealth.ckblib.graph.model.BaseRelation;
import reactor.core.publisher.Mono;

@Service
public class BaseRelationService {

    @Autowired
    Driver driver;

    @Autowired
    BaseRelationRepository baseRelationRepository;

    public String getRelationsByCui(String cui) {
        String externalQuery = "MATCH (d {cui: '" + cui + "'}) -[r]- (s) RETURN d, r, s";
        D3Map d3Map = new D3Map.Builder(driver).withQuery(externalQuery).build();
        return d3Map.fetch();
    }

    public Mono<BaseRelation> addAttributes(String dcui, String scui, int confidence, int upvote, int downvote) {
            return baseRelationRepository.updateRelationshipAttributes(dcui, scui, confidence, upvote, downvote);
    }
}
