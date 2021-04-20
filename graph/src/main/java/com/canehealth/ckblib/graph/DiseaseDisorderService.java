package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

@Service
public class DiseaseDisorderService {


    @Autowired
    DiseaseDisorderRepository diseaseDisorderMentionRepository;



    @Autowired
    Driver driver;


    public Mono<DiseaseDisorderMention> getDiseaseByCui(String cui) {
        return diseaseDisorderMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<DiseaseDisorderMention> saveDisease(DiseaseDisorderMention diseaseDisorderMention){
        return diseaseDisorderMentionRepository.save(diseaseDisorderMention);
    }

    public Mono<DiseaseDisorderMention> getDiseasesByName(String name){
        return diseaseDisorderMentionRepository.findOneByNameLikeIgnoreCase(name);
    }

    public Mono<DiseaseDisorderMention> addDifferential(String dcui, String ddcui, int confidence, int upvote, int downvote) {
        return diseaseDisorderMentionRepository.mergeDiseaseWithDisease(dcui, ddcui, confidence, upvote, downvote);
    }

    public String forD3(String cui) {

        return d3MapBuilder.build("Disease", cui, "Symptom", "", "PRESENTATION_OF");

    }
}
