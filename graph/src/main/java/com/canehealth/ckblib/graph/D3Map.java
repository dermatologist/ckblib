/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.canehealth.ckblib.graph;
// https://github.com/neo4j-examples/movies-java-spring-data-neo4j/tree/sdn6-full-example
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import net.minidev.json.JSONObject;

@Data
public class D3Map {

    // Builder pattern
    // https://dzone.com/articles/design-patterns-the-builder-pattern
    private static Logger LOG = LoggerFactory.getLogger(D3Map.class);


    private String cui;
    private String secondCui = "";
    private String concept;
    private String features;
    private String relationship;
    private String externalQuery;
    private Driver driver;
    public static class Builder {
        private String cui;
        private String secondCui;
        private String concept;
        private String features;
        private String relationship;
        private String externalQuery;
        private Driver driver;

        public Builder(Driver driver){
            this.driver = driver;
        }

        public Builder withCui(String cui) {
            this.cui = cui;
            return this;
        }

        public Builder withSecondCui(String scui) {
            this.secondCui = scui;
            return this;
        }

        public Builder withConcept(String concept) {
            this.concept = concept;
            return this;
        }

        public Builder withFeatures(String features) {
            this.features = features;
            return this;
        }

        public Builder withRelationship(String relationship) {
            this.relationship = relationship;
            return this;
        }

        public Builder withQuery(String externalQuery) {
            this.externalQuery = externalQuery;
            return this;
        }

        public D3Map build() {

            D3Map d3Map = new D3Map();
            d3Map.driver = this.driver;
            d3Map.cui = this.cui;
            d3Map.secondCui = this.secondCui;
            d3Map.concept = this.concept;
            d3Map.features = this.features;
            d3Map.relationship = this.relationship;
            d3Map.externalQuery = this.externalQuery;
            return d3Map;

        }
    }

    // Fields omitted for brevity.
    private D3Map() {
        //Constructor is now private.
    }

    public String getQueryWithAll(String _concept, String cCUI, String _features, String fCUI, String _relationship){
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

    public String getQuery(String cui){
        return "MATCH (d {cui: '" + cui + "'}) -[r]- (s) RETURN d.name AS concept, collect(s.name) AS features, collect(r.confidence) AS value";
    }

    public String query() {
        String q = "";
        if("".equals(this.secondCui) || this.secondCui == null)
            q = this.getQuery(this.cui);
        else
            q = getQueryWithAll(this.concept, this.cui, this.features, this.secondCui, this.relationship);
        return createMap(q);
    }

    public String fetch(){
        return createJson(this.externalQuery);
    }

    public String createMap(String query){
        var nodes = new ArrayList<>();
        var links = new ArrayList<>();

        // Disease to Concept and Symptoms to features and confidence as values
        // group 1 is disease, group 2 other
        LOG.info(query);
        try (Session session = driver.session()) {
            var records = session.readTransaction(tx -> tx.run(query).list());
            records.forEach(record -> {
                var disease = Map.of("group", 1, "id", record.get("concept").asString());

                nodes.add(disease);

                record.get("features").asList(v -> v.asString()).forEach(name -> {
                    int color = 2;
                    if(Character.isUpperCase(name.charAt(0)))
                        color = 3;
                    if(name.length()>50)
                        color = 4;
                    var features = Map.of("group", color, "id", name);

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

    public String createJson(String query){
        LOG.info(query);
        var results = new ArrayList<Record>();
        try (Session session = driver.session()) {
            results = (ArrayList<Record>) session.readTransaction(tx -> tx.run(query).list());
        }
        String json = new Gson().toJson(results);
        return json;
    }

}
