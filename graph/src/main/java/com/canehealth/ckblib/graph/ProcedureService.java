package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.ProcedureMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProcedureService {

    @Autowired
    ProcedureRepository procedureMentionRepository;



    @Autowired
    Driver driver;

    public Mono<ProcedureMention> getSymptomByCui(String cui) {
        return procedureMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<ProcedureMention> saveSymptom(ProcedureMention procedureMention) {
        return procedureMentionRepository.save(procedureMention);
    }

    public Flux<ProcedureMention> getSymptomByName(String name) {
        return procedureMentionRepository.findAllByNameLikeIgnoreCase(name);
    }

    public Mono<ProcedureMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
        return procedureMentionRepository.mergeDiseaseWithProcedure(dcui, scui, confidence, upvote, downvote);
    }


    public String forD3(String cui) {

        return d3MapBuilder.build("Disease", "", "Procedure", cui, "TREATMENT_OF");

    }

}
