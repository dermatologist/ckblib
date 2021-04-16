package com.canehealth.ckblib.graph.model;

import java.util.List;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import lombok.Data;

@RelationshipProperties
@Data
public class WeightAttributes {

    private List<JournalArticle> confidence;

    private int upVote;

    private int downVote;
}
