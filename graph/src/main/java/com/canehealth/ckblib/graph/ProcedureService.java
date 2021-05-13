/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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

    /**
     * Returns all procedures in the graph
     *
     * @see com.canehealth.ckblib.graph.ProcedureRepository
     * @author beapen
     * @since 0.14.0
     * @return All procedure nodes in the graph
     */
    public Flux<ProcedureMention> getAllProcedures() {
        return procedureMentionRepository.findAll();
    }

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
        D3Map d3Map = new D3Map.Builder(driver).withCui(cui).build();
        return d3Map.query();
    }

}
