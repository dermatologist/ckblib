/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */


package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.BaseRelation;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BaseRelationRepository extends ReactiveNeo4jRepository<BaseRelation, Long> {

    @Query("" + "MATCH (d {cui: $cui}) -[r]- (s) \n" + "RETURN r")
    Mono<BaseRelation> getRelationsByCui(String cui);

    @Query(""
		+ "MATCH (d {cui: $dcui}) -[r]- (s {cui: $scui})\n"
        + "SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d  \n"
	)
    Mono<Object> updateRelationshipAttributes(String dcui, String scui, int c, int u, int d);

    // @Query(""
	// 	+ "MATCH (d {cui: $cui}) -[r]- (s)\n"
    //     + "RETURN sum(r.upvote) - sum(r.downvote) as lift, d.name as name, d.cui as cui \n"
	// )
    // Mono<Object> getLift(String cui);
}
