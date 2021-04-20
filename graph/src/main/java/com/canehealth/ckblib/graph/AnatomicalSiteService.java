package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.AnatomicalSiteMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnatomicalSiteService {

    @Autowired
    AnatomicalSiteRepository anatomicalSiteMentionRepository;



    @Autowired
    Driver driver;

    public Mono<AnatomicalSiteMention> getSymptomByCui(String cui) {
        return anatomicalSiteMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<AnatomicalSiteMention> saveSymptom(AnatomicalSiteMention anatomicalSiteMention) {
        return anatomicalSiteMentionRepository.save(anatomicalSiteMention);
    }

    public Flux<AnatomicalSiteMention> getSymptomByName(String name) {
        return anatomicalSiteMentionRepository.findAllByNameLikeIgnoreCase(name);
    }


    public Mono<AnatomicalSiteMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
        return anatomicalSiteMentionRepository.mergeDiseaseWithAnatomy(dcui, scui, confidence, upvote, downvote);
    }

    public String forD3(String cui) {

        return d3MapBuilder.build("Disease", "", "Anatomy", cui, "ANATOMY_OF");

    }

}
