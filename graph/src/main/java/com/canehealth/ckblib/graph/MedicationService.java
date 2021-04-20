package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.MedicationMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MedicationService {

    @Autowired
    MedicationRepository medicationMentionRepository;



    @Autowired
    Driver driver;

    public Mono<MedicationMention> getSymptomByCui(String cui) {
        return medicationMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<MedicationMention> saveSymptom(MedicationMention medicationMention) {
        return medicationMentionRepository.save(medicationMention);
    }

    public Flux<MedicationMention> getSymptomByName(String name) {
        return medicationMentionRepository.findAllByNameLikeIgnoreCase(name);
    }

    public Mono<MedicationMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
        return medicationMentionRepository.mergeDiseaseWithMedication(dcui, scui, confidence, upvote, downvote);
    }


    public String forD3(String cui) {

        return d3MapBuilder.build("Disease", "", "Medication", cui, "ASSOCIATION_OF");

    }

}
