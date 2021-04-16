package com.canehealth.ckblib.graph;

import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.Data;

@RelationshipProperties
@Data
public class DifferentialDiagnosis {

    @Id
    @GeneratedValue
    private Long id;
    private String DDid;

    private List<JournalArticle> confidence;

    private int upVote;

    private int downVote;

    @TargetNode
    DiseaseDisorderMention targetDisease;



}
