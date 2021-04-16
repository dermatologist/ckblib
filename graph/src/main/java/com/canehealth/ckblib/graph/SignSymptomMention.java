package com.canehealth.ckblib.graph;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Node("Symptom")
@AllArgsConstructor
public class SignSymptomMention {
    @Id
    private final String cui;

    @Getter
    private final String name;

    @Relationship(type = "HAS_ATTRIBUTES", direction = OUTGOING)
    private List<ConceptAttributes> attributes = new ArrayList<>();

    @Relationship(value = "IS_SYMPTOM", direction = INCOMING)
    private final List<DiseaseSymptomRelation> diseases;
}
