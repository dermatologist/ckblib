package com.canehealth.ckblib.graph.repository;


import java.util.List;
import java.util.Optional;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "disease")
public interface DiseaseDisorderRepository extends Repository<DiseaseDisorderMention, String> {
    List<DiseaseDisorderMention> findAll();

    Optional<DiseaseDisorderMention> findById(String cui);

    Optional<DiseaseDisorderMention> findOneByCui(String cui);

    List<DiseaseDisorderMention> findAllByNameLikeIgnoreCase(String name);

    @RestResource(exported = false)
    List<DiseaseDisorderMention> findAllBySymptomsName(String name);

    @RestResource(exported = false)
    List<DiseaseDisorderMention> findAllByAnatomyName(String name);

    @RestResource(exported = false)
    List<DiseaseDisorderMention> findAllByTreatmentName(String name);

    @RestResource(exported = false)
    List<DiseaseDisorderMention> findAllByProceduresName(String name);

    // @RestResource(exported = false)
    // @Query("" + "MATCH p = shortestPath( (p1:Person {name: $name1})-[:ACTED_IN*]-(target:Person {name: $name2}) )\n"
    //         + "WITH [n IN nodes(p) WHERE n:Person | n] as bacon\n" + "UNWIND(bacon) AS person\n"
    //         + "RETURN DISTINCT person")
    // List<Person> findBaconPeopleBetween(String name1, String name2);

    // @Query("MATCH (m:Movie {title: $movie}) <-[:ACTED_IN]- (p:Person) RETURN p")
    // List<Person> findAllThatActedInMovie(String movie);

    // @Query("" + "MATCH (person:Person {name: $name})\n" + "OPTIONAL MATCH (person)-[:DIRECTED]->(d:Movie)\n"
    //         + "OPTIONAL MATCH (person)<-[r:ACTED_IN]->(a:Movie)\n"
    //         + "OPTIONAL MATCH (person)-->(movies)<-[relatedRole:ACTED_IN]-(relatedPerson)\n"
    //         + "RETURN DISTINCT person,\n" + "collect(DISTINCT d) AS directed,\n" + "collect(DISTINCT a) AS actedIn,\n"
    //         + "collect(DISTINCT relatedPerson) AS related")
    // PersonDetails getDetailsByName(String name);
}
