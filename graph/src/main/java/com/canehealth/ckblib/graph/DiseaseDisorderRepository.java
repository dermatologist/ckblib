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

    // Mono<DiseaseDisorderMention> findById(String cui);

    Mono<DiseaseDisorderMention> findOneByCui(String cui);

    Flux<DiseaseDisorderMention> findAllByNameLikeIgnoreCase(String name);

    Mono<DiseaseDisorderMention> findOneByNameLikeIgnoreCase(String name);

    @Query("" + "MATCH (d:Disease {cui: $dcui}) MATCH (dd:Disease {cui: $ddcui})\n"
            + "MERGE (d) <-[r:ASSOCIATED_WITH]- (dd) \n"
            + "ON CREATE SET r.confidence = 1, r.upvote = 0, r.downvote = 0 \n"
            + "ON MATCH SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d  \n"
            + "RETURN DISTINCT dd")
    Mono<DiseaseDisorderMention> mergeDiseaseWithDisease(String dcui, String ddcui, int c, int u, int d);

}
