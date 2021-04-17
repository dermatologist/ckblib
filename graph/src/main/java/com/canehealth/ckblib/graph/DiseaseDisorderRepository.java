package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.model.SignSymptomMention;

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

    @Query("MATCH (d:Disease {cui: $cui}) -[:PRESENTS_WITH]-> (s:Symptom) RETURN s")
    Flux<SignSymptomMention> findAllSymptomsByCui(String cui);

}
