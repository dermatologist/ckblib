package com.canehealth.ckblib.graph.model;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.stereotype.Component;

import lombok.Data;
@Node("Disease")
@Data
@Component
public class DiseaseDisorderMention {

    @Id
    private String cui;

    private String name;

    @Version
    private Long version;


    @Relationship(type = "HAS_ATTRIBUTES", direction = OUTGOING)
    private List<ConceptAttributes> attributes = new ArrayList<>();

    // target is in differential diagnosis
    @Relationship(type = "ASSOCIATED_WITH", direction = INCOMING)
    private List<BaseRelation> differentials = new ArrayList<>();


}
