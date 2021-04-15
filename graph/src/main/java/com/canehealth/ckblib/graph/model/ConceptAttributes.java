package com.canehealth.ckblib.graph.model;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import lombok.Data;

@RelationshipProperties
@Data
public class ConceptAttributes {

    private final String codingScheme;

    private final String name;

    private final String code;

    private final String tui;
}
