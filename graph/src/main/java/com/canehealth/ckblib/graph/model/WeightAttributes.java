package com.canehealth.ckblib.graph.model;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import lombok.Data;

@RelationshipProperties
@Data
public class WeightAttributes {

    private int support;

    private int confidence;

    private int upVote;

    private int downVote;
}
