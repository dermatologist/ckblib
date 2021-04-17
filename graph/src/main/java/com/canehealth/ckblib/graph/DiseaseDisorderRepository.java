package com.canehealth.ckblib.graph;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DiseaseDisorderRepository extends ReactiveNeo4jRepository <DiseaseDisorderMention, String> {

    Flux<DiseaseDisorderMention> findAll();

    Mono<DiseaseDisorderMention> findById(String cui);

    Mono<DiseaseDisorderMention> findOneByCui(String cui);

    Flux<DiseaseDisorderMention> findAllByNameLikeIgnoreCase(String name);

}
