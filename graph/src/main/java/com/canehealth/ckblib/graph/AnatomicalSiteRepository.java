package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.AnatomicalSiteMention;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AnatomicalSiteRepository extends ReactiveNeo4jRepository<AnatomicalSiteMention, String> {

    Flux<AnatomicalSiteMention> findAll();

    Mono<AnatomicalSiteMention> findOneByCui(String cui);

    Flux<AnatomicalSiteMention> findAllByNameLikeIgnoreCase(String name);

    @Query("" + "MATCH (d:Disease {cui: $dcui}), (s:Anatomy {cui: $scui})\n" + "MERGE (d) <-[r:ANATOMY_OF]- (s) \n"
            + "ON CREATE SET r.confidence = 1, r.upvote = 0, r.downvote = 0 \n"
            + "ON MATCH SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d  \n"
            + "RETURN DISTINCT s")
    Mono<AnatomicalSiteMention> mergeDiseaseWithAnatomy(String dcui, String scui, int c, int u, int d);

}
