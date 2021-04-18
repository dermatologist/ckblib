package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.SignSymptomMention;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SignSymptomRepository extends ReactiveNeo4jRepository<SignSymptomMention, String> {

    Flux<SignSymptomMention> findAll();

    // Mono<DiseaseDisorderMention> findById(String cui);

    Mono<SignSymptomMention> findOneByCui(String cui);

    Flux<SignSymptomMention> findAllByNameLikeIgnoreCase(String name);


    // @Query("MATCH (d:Disease {cui: $dcui}), (s:Symptom {cui: $scui}) MERGE (d) <-[:PRESENTATION_OF]- (s) RETURN s")
    // Mono<SignSymptomMention> mergeDiseaseWithSymptom(String dcui, String scui);

    @Query(""
		+ "MATCH (d:Disease {cui: $dcui}), (s:Symptom {cui: $scui})\n"
		+ "MERGE (d) <-[r:PRESENTATION_OF]- (s) \n"
        + "ON CREATE SET r.confidence = 0, r.upvote = 0, r.downvote = 0 \n"
        + "ON MATCH SET r.confidence = r.confidence + 1 \n"
		+ "RETURN DISTINCT s"
	)
    Mono<SignSymptomMention> mergeDiseaseWithSymptom(String dcui, String scui);

    // @Query("" + "MATCH (d:Disease {cui: $dcui}) <-[r:PRESENTATION_OF]-  (s:Symptom {cui: $scui})\n"
    //           + "SET r.weights = r.weights + 1 RETURN DISTINCT s")
    // Mono<SignSymptomMention> updateWeights(String dcui, String scui);

}
