/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.JournalArticle;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface JournalArticleRepository extends ReactiveNeo4jRepository<JournalArticle, String> {



    Mono<JournalArticle> findOneByPmid(String cui);

    Flux<JournalArticle> findAllByNameLikeIgnoreCase(String name);

    @Query("" + "MATCH (c) WHERE c.cui=$cui \n"
              + "MATCH (j:Article {pmid: $pmid})\n"
              + "MERGE (c) <-[:EVIDENCE]- (j) \n"
              + "RETURN j")
    Mono<JournalArticle> mergeConceptWithEvidence(String cui, String pmid);

}
