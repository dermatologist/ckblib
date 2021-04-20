package com.canehealth.ckblib.graph.util;
// https://github.com/neo4j-examples/movies-java-spring-data-neo4j/tree/sdn6-full-example
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class D3MapBuilder {

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
        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        /*
         query = ""       + " MATCH (d:Disease) <- [r:PRESENTATION_OF] - (s:Symptom {cui: '"
                            + cui
                            + "'})"
                            + " WITH d, s, r ORDER BY r.confidence, d.name"
                            + " RETURN d.name AS disease, collect(s.name) AS symptoms";
        */
        String query = getQuery( _concept,  cCUI,  _features,  fCUI,  _relationship);
        // Disease to Concept and Symptoms to features and confidence as values
        try (Session session = driver.session()) {
            var records = session
                    .readTransaction(tx -> tx.run(query).list());
            records.forEach(record -> {
                var concept = Map.of("label", "id", "title", record.get("concept").asString());

                // value is confidence
                int value = 1;
                try{
                    value = record.get("value").asInt();
                } catch (Exception e) {
                }

                final int finalvalue = value; //should be final

                var targetIndex = nodes.size();
                nodes.add(concept);

                record.get("features").asList(v -> v.asString()).forEach(name -> {
                    var features = Map.of("label", "group", "title", record.get("features").asList().indexOf(name));

                    int sourceIndex;
                    if (nodes.contains(features)) {
                        sourceIndex = nodes.indexOf(features);
                    } else {
                        nodes.add(features);
                        sourceIndex = nodes.size() - 1;
                    }
                    links.add(Map.of("source", nodes.get(sourceIndex), "target", nodes.get(targetIndex), "value", finalvalue));
                });
            });
        }
        var d3graph = Map.of("nodes", nodes, "links", links);
        JSONObject json = new JSONObject();
        json = new JSONObject(d3graph);
        return json.toString();
    }

    /*
     * Debug
     *
     * {"nodes":[{"label":"id","title":"Psoriasis Vulgaris"},{"label":"group",
     * "title":0}],"links":[{"source":{"label":"group","title":0},"value":1,"target"
     * :{"label":"id","title":"Psoriasis Vulgaris"}}]}
     */
}
