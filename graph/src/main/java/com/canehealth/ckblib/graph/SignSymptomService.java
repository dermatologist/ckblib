package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.SignSymptomMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

@Service
public class SignSymptomService {

    @Autowired
    SignSymptomRepository signSymptomMentionRepository;

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

        // return d3MapBuilder.build("Disease", "", "Symptom", cui, "PRESENTATION_OF");

        D3Map d3Map = new D3Map.Builder(driver).withCui(cui).build();
        return d3Map.query();

    }
}
