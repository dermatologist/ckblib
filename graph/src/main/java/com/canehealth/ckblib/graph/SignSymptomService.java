/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.SignSymptomMention;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SignSymptomService {

    @Autowired
    SignSymptomRepository signSymptomMentionRepository;

    @Autowired
    Driver driver;

    /**
     * Returns all procedures in the graph
     *
     * @see com.canehealth.ckblib.graph.SignSymptomRepository
     * @author beapen
     * @since 0.14.0
     * @return All sign-symptom nodes in the graph
     */
    public Flux<SignSymptomMention> getAllSignSymptoms() {
        return signSymptomMentionRepository.findAll();
    }

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

    public Mono<SignSymptomMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
            return signSymptomMentionRepository.mergeDiseaseWithSymptom(dcui, scui, confidence, upvote, downvote);
    }

    public String forD3(String cui) {
        D3Map d3Map = new D3Map.Builder(driver).withCui(cui).build();
        return d3Map.query();

    }
}
