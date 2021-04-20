package com.canehealth.ckblib.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.canehealth.ckblib.graph.model.SignSymptomMention;
import com.canehealth.ckblib.graph.util.D3MapBuilder;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

@Service
public class SignSymptomService {

    @Autowired
    SignSymptomRepository signSymptomMentionRepository;

    @Autowired
    D3MapBuilder d3MapBuilder;

    @Autowired
    Driver driver;

    public Mono<SignSymptomMention> getSymptomByCui(String cui) {
        return signSymptomMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<SignSymptomMention> saveSymptom(SignSymptomMention signSymptomMention) {
        return signSymptomMentionRepository.save(signSymptomMention);
    }

    public Mono<SignSymptomMention> getSymptomByName(String name) {
        return signSymptomMentionRepository.findOneByNameLikeIgnoreCase(name);
    }

    // public Flux<DiseaseDisorderMention> getDiseases(String cui) {
    //     return signSymptomMentionRepository.findAllDiseasesWithSymptomsByCui(cui);
    // }

    public Mono<SignSymptomMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
            return signSymptomMentionRepository.mergeDiseaseWithSymptom(dcui, scui, confidence, upvote, downvote);
    }

    public String forD3(String cui) {

        return d3MapBuilder.build("Disease", "", "Symptom", cui, "PRESENTATION_OF");

    }
}
