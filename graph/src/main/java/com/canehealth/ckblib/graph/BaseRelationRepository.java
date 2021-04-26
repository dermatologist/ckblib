package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.BaseRelation;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface BaseRelationRepository extends ReactiveNeo4jRepository<BaseRelation, Long> {

    // Flux<BaseRelation> findAll();

    @Query("" + "MATCH (d {cui: $cui}) -[r]- (s) \n" + "RETURN r")
    Mono<BaseRelation> getRelationsByCui(String cui);

    @Query(""
		+ "MATCH (d {cui: $dcui}) -[r]- (s {cui: $scui})\n"
        + "SET r.confidence = r.confidence +  $c, r.upvote = r.upvote +  $u, r.downvote = r.downvote +  $d  \n"
	)
    Mono<Object> updateRelationshipAttributes(String dcui, String scui, int c, int u, int d);

}
