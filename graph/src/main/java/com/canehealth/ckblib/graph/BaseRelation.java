package com.canehealth.ckblib.graph;

import com.canehealth.ckblib.graph.model.DiseaseDisorderMention;

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

    private WeightAttributes weights;

    @TargetNode
    DiseaseDisorderMention diseaseDisorderMention;
}
