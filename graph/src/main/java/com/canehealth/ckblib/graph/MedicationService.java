/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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

    /**
     * Returns all medications in the graph
     *
     * @see com.canehealth.ckblib.graph.MedicationRepository
     * @author beapen
     * @since 0.14.0
     * @return All medication nodes in the graph
     */
    public Flux<MedicationMention> getAllMedications() {
        return medicationMentionRepository.findAll();
    }

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
        D3Map d3Map = new D3Map.Builder(driver).withCui(cui).build();
        return d3Map.query();
    }

}
