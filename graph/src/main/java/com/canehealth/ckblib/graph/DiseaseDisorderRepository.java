/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DiseaseDisorderRepository extends ReactiveNeo4jRepository <DiseaseDisorderMention, String> {

    Flux<DiseaseDisorderMention> findAll();

    Mono<DiseaseDisorderMention> findOneByCui(String cui);

    Flux<DiseaseDisorderMention> findAllByNameLikeIgnoreCase(String name);

    Mono<DiseaseDisorderMention> findOneByNameLikeIgnoreCase(String name);

    @Query(
    ""+"MATCH\n"
    + "(d:Disease {cui: $dcui}),\n"
    + "(o:Disease {cui: $ddcui})\n"
    + "MERGE (d)-[r:ASSOCIATED_WITH]-(o)\n"
    + "ON CREATE SET r.confidence = 1, r.upvote = 0, r.downvote = 0 \n"
    + "ON MATCH SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d  \n"
    )
    Mono<Object> mergeDiseaseWithDisease(String dcui, String ddcui, int c, int u, int d);

}
