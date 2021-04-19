package com.canehealth.ckblib.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.canehealth.ckblib.graph.model.MedicationMention;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MedicationService {

    @Autowired
    MedicationRepository medicationMentionRepository;

    @Autowired
    Driver driver;

    public Mono<MedicationMention> getSymptomByCui(String cui) {
        return medicationMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<MedicationMention> saveSymptom(MedicationMention medicationMention) {
        return medicationMentionRepository.save(medicationMention);
    }

    public Flux<MedicationMention> getSymptomByName(String name) {
        return medicationMentionRepository.findAllByNameLikeIgnoreCase(name);
    }

    public Mono<MedicationMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
        return medicationMentionRepository.mergeDiseaseWithMedication(dcui, scui, confidence, upvote, downvote);
    }

    /**
     * This is an example of when you might want to use the pure driver in case you
     * have no need for mapping at all, neither in the form of the way the
     * {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in
     * form of entities.
     *
     * @return A representation D3.js can handle
     */
    public Map<String, List<Object>> forD3(String cui) {

        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        try (Session session = driver.session()) {
            var records = session.readTransaction(tx -> tx.run("" + " MATCH (d:Disease) <- [r:ASSOCIATION_OF] - (s:Medication {cui: '"
                    + cui
                    + "'})"
                    + " WITH d, s, r ORDER BY r.confidence, d.name"
                    + " RETURN d.name AS disease, collect(s.name) AS medications").list());
            records.forEach(record -> {
                var disease = Map.of("label", "disease", "title", record.get("disease").asString());

                var targetIndex = nodes.size();
                nodes.add(disease);

                record.get("medications").asList(v -> v.asString()).forEach(name -> {
                    var medication = Map.of("label", "medication", "title", name);

                    int sourceIndex;
                    if (nodes.contains(medication)) {
                        sourceIndex = nodes.indexOf(medication);
                    } else {
                        nodes.add(medication);
                        sourceIndex = nodes.size() - 1;
                    }
                    links.add(Map.of("source", sourceIndex, "target", targetIndex));
                });
            });
        }
        return Map.of("nodes", nodes, "links", links);
    }

}
