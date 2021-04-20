package com.canehealth.ckblib.graph.util;
// https://github.com/neo4j-examples/movies-java-spring-data-neo4j/tree/sdn6-full-example
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;

public class D3MapBuilder {

    @Autowired
    Driver driver;

    public String build(String query) {
    /**
     * This is an example of when you might want to use the pure driver in case you
     * have no need for mapping at all, neither in the form of the way the
     * {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in
     * form of entities.
     *
     * @return A representation D3.js can handle
     */
        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        try (Session session = driver.session()) {
            var records = session
                    .readTransaction(tx -> tx.run(query).list());
            records.forEach(record -> {
                var disease = Map.of("label", "disease", "title", record.get("disease").asString());

                var targetIndex = nodes.size();
                nodes.add(disease);

                record.get("symptoms").asList(v -> v.asString()).forEach(name -> {
                    var symptom = Map.of("label", "symptom", "title", name);

                    int sourceIndex;
                    if (nodes.contains(symptom)) {
                        sourceIndex = nodes.indexOf(symptom);
                    } else {
                        nodes.add(symptom);
                        sourceIndex = nodes.size() - 1;
                    }
                    links.add(Map.of("source", sourceIndex, "target", targetIndex));
                });
            });
        }
        var d3graph = Map.of("nodes", nodes, "links", links);
        JSONObject json = new JSONObject();
        json = new JSONObject(d3graph);
        return json.toString();
    }
}
