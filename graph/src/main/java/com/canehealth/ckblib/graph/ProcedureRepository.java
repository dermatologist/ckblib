/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.ProcedureMention;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProcedureRepository extends ReactiveNeo4jRepository<ProcedureMention, String> {

    Flux<ProcedureMention> findAll();

    Mono<ProcedureMention> findOneByCui(String cui);

    Flux<ProcedureMention> findAllByNameLikeIgnoreCase(String name);

    @Query("" + "MATCH (d:Disease {cui: $dcui}) MATCH (s:Procedure {cui: $scui})\n"
            + "MERGE (d) <-[r:TREATMENT_OF]- (s) \n"
            + "ON CREATE SET r.confidence = 1, r.upvote = 0, r.downvote = 0 \n"
            + "ON MATCH SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d  \n"
            + "RETURN DISTINCT s")
    Mono<ProcedureMention> mergeDiseaseWithProcedure(String dcui, String scui, int c, int u, int d);

}
