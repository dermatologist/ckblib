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
    BaseRelationRepository baseRelationRepository;

    @Autowired
    Driver driver;

    public Mono<BaseRelation> getRelationsByCui(String cui) {
        return baseRelationRepository.getRelationsByCui(cui);
    }


}
