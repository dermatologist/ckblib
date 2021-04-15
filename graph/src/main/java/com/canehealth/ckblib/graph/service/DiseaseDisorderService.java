package com.canehealth.ckblib.graph.service;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;
import com.canehealth.ckblib.graph.repository.DiseaseDisorderReactiveRepository;
import com.canehealth.ckblib.graph.repository.DiseaseDisorderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiseaseDisorderService {

    private final DiseaseDisorderRepository diseaseDisorderRepository;
    private final DiseaseDisorderReactiveRepository diseaseDisorderReactiveRepository;
    private final Driver driver;

    DiseaseDisorderService(DiseaseDisorderRepository diseaseDisorderRepository, DiseaseDisorderReactiveRepository diseaseDisorderReactiveRepository, Driver driver) {
		this.diseaseDisorderRepository = diseaseDisorderRepository;
        this.diseaseDisorderReactiveRepository = diseaseDisorderReactiveRepository;
		this.driver = driver;
	}

    public Mono<DiseaseDisorderMention> getDiseaseByCui(String cui) {
        return diseaseDisorderReactiveRepository.findById(cui);
    }

    public List<DiseaseDisorderMention> withSymptoms(String name) {
        return diseaseDisorderRepository.findAllBySymptomsName(name);
    }

    public Flux<DiseaseDisorderMention> withReactiveSymptoms(String name) {
        return diseaseDisorderReactiveRepository.findAllBySymptomsName(name);
    }

    public List<DiseaseDisorderMention> withAnatomy(String name) {
        return diseaseDisorderRepository.findAllByAnatomyName(name);
    }

    public Flux<DiseaseDisorderMention> withReactiveAnatomy(String name) {
        return diseaseDisorderReactiveRepository.findAllByAnatomyName(name);
    }

    public List<DiseaseDisorderMention> withTreatment(String name) {
        return diseaseDisorderRepository.findAllByTreatmentName(name);
    }

    public Flux<DiseaseDisorderMention> withReactiveTreatment(String name) {
        return diseaseDisorderReactiveRepository.findAllByTreatmentName(name);
    }

    public List<DiseaseDisorderMention> withProcedure(String name) {
        return diseaseDisorderRepository.findAllByProceduresName(name);
    }

    public Flux<DiseaseDisorderMention> withReactiveProcedure(String name) {
        return diseaseDisorderReactiveRepository.findAllByProceduresName(name);
    }
    /**
     * This is an example of when you might want to use the pure driver in case you
     * have no need for mapping at all, neither in the form of the way the
     * {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in
     * form of entities.
     *
     * @return A representation D3.js can handle
     */
    public Map<String, List<Object>> f() {

        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        try (Session session = driver.session()) {
            var records = session.readTransaction(tx -> tx.run("" + " MATCH (m:DiseaseDisorderMention) <- [r:ACTED_IN] - (p:Person)"
                    + " WITH m, p ORDER BY m.title, p.name" + " RETURN m.title AS movie, collect(p.name) AS actors")
                    .list());
            records.forEach(record -> {
                var movie = Map.of("label", "movie", "title", record.get("movie").asString());

                var targetIndex = nodes.size();
                nodes.add(movie);

                record.get("actors").asList(v -> v.asString()).forEach(name -> {
                    var actor = Map.of("label", "actor", "title", name);

                    int sourceIndex;
                    if (nodes.contains(actor)) {
                        sourceIndex = nodes.indexOf(actor);
                    } else {
                        nodes.add(actor);
                        sourceIndex = nodes.size() - 1;
                    }
                    links.add(Map.of("source", sourceIndex, "target", targetIndex));
                });
            });
        }
        return Map.of("nodes", nodes, "links", links);


    }

}
