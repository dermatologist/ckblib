/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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

    /**
     * Returns all diseases in the graph
     *
     * @see com.canehealth.ckblib.graph.AnatomicalSiteRepository
     * @author beapen
     * @since 0.14.0
     * @return All anatomy nodes in the graph
    */
    public Flux<DiseaseDisorderMention> getAllAnatomies(){
        return anatomicalSiteMentionRepository.findAll();
    }

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

        D3Map d3Map = new D3Map.Builder(driver).withCui(cui).build();
        return d3Map.query();
    }

}
