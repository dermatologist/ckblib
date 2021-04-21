package com.canehealth.ckblib.graph.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.Data;

@Data
@RelationshipProperties
public class BaseRelation {

    @Id
    @GeneratedValue
    private Long id;

    private Long confidence;

    private Long upvote;

    private Long downvote;

    @TargetNode
    DiseaseDisorderMention diseaseDisorderMention;
}
