package com.canehealth.ckblib.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.canehealth.ckblib.graph.model.AnatomicalSiteMention;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnatomicalSiteService {

    @Autowired
    AnatomicalSiteRepository anatomicalSiteMentionRepository;

    @Autowired
    Driver driver;

    public Mono<AnatomicalSiteMention> getSymptomByCui(String cui) {
        return anatomicalSiteMentionRepository.findOneByCui(cui);
    }

    @Transactional
    public Mono<AnatomicalSiteMention> saveSymptom(AnatomicalSiteMention anatomicalSiteMention) {
        return anatomicalSiteMentionRepository.save(anatomicalSiteMention);
    }

    public Flux<AnatomicalSiteMention> getSymptomByName(String name) {
        return anatomicalSiteMentionRepository.findAllByNameLikeIgnoreCase(name);
    }


    public Mono<AnatomicalSiteMention> addRelation(String dcui, String scui, int confidence, int upvote, int downvote) {
        return anatomicalSiteMentionRepository.mergeDiseaseWithAnatomy(dcui, scui, confidence, upvote, downvote);
    }

    /**
     * This is an example of when you might want to use the pure driver in case you
     * have no need for mapping at all, neither in the form of the way the
     * {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in
     * form of entities.
     *
     * @return A representation D3.js can handle
     */
    public Map<String, List<Object>> forD3() {

        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        try (Session session = driver.session()) {
            var records = session
                    .readTransaction(tx -> tx.run("" + " MATCH (d:Disease) <- [r:ANATOMY_OF] - (s:Anatomy)"
                            + " WITH d, s, r ORDER BY r.confidence, d.name"
                            + " RETURN d.name AS disease, collect(s.name) AS anatomys").list());
            records.forEach(record -> {
                var disease = Map.of("label", "disease", "title", record.get("disease").asString());

                var targetIndex = nodes.size();
                nodes.add(disease);

                record.get("anatomys").asList(v -> v.asString()).forEach(name -> {
                    var anatomy = Map.of("label", "anatomy", "title", name);

                    int sourceIndex;
                    if (nodes.contains(anatomy)) {
                        sourceIndex = nodes.indexOf(anatomy);
                    } else {
                        nodes.add(anatomy);
                        sourceIndex = nodes.size() - 1;
                    }
                    links.add(Map.of("source", sourceIndex, "target", targetIndex));
                });
            });
        }
        return Map.of("nodes", nodes, "links", links);
    }

}
