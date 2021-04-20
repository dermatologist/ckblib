package com.canehealth.ckblib.graph;
// https://github.com/neo4j-examples/movies-java-spring-data-neo4j/tree/sdn6-full-example
import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

@Component
@NoArgsConstructor
public class D3MapBuilder {

    private static Logger LOG = LoggerFactory.getLogger(D3MapBuilder.class);

    @Autowired
    Driver driver;

    public String getQuery(String _concept, String cCUI, String _features, String fCUI, String _relationship){
        String query = "";
        if ("".equals(cCUI))
            query = query + " MATCH (d:" + _concept + ") <- [r:";
        else
            query = query + " MATCH (d:" + _concept + " {cui: '" + cCUI + "'}) <- [r:";
        query = query + _relationship + "] - (s:";
        if ("".equals(fCUI))
            query = query + _features + fCUI + ")";
        else
            query = query + _features + " {cui: '" + fCUI + "'})";
        query = query + " WITH d, s, r ORDER BY r.confidence, d.name";
        query = query + " RETURN d.name AS concept, collect(s.name) AS features, collect(r.confidence) AS value";
        LOG.debug(query);
        return query;
    }

    public String build(String _concept, String cCUI, String _features, String fCUI, String _relationship) {
    /**
     * This is an example of when you might want to use the pure driver in case you
     * have no need for mapping at all, neither in the form of the way the
     * {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in
     * form of entities.
     *
     * @return A representation D3.js can handle
     */
        String query = getQuery(_concept, cCUI, _features, fCUI, _relationship);
        return createMap(query);

    }

    public String createMap(String query){
        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        // Disease to Concept and Symptoms to features and confidence as values
        // group 1 is disease, group 2 other
        try (Session session = driver.session()) {
            var records = session.readTransaction(tx -> tx.run(query).list());
            records.forEach(record -> {
                var disease = Map.of("group", 1, "id", record.get("concept").asString());

                nodes.add(disease);

                record.get("features").asList(v -> v.asString()).forEach(name -> {
                    var features = Map.of("group", 2, "id", name);

                    if (nodes.contains(features)) {
                    } else {
                        nodes.add(features);
                    }

                    try { // Cannot coerce LIST OF ANY? to Java int
                        links.add(Map.of("source", record.get("concept").asString(), "target", name, "value",
                                record.get("value").asInt()));
                    } catch (Exception e) {
                        links.add(Map.of("source", record.get("concept").asString(), "target", name, "value", 1));
                    }
                });
            });
        }
        var d3graph = Map.of("nodes", nodes, "links", links);
        JSONObject json = new JSONObject();
        json = new JSONObject(d3graph);
        return json.toString();

    }

}
