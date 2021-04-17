package com.canehealth.ckblib.graph.model;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.*;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Node("Anatomy")
@AllArgsConstructor
public class AnatomicalSiteMention {
    @Id
    private String cui;

    @Getter
    private String name;

    @Version
    private Long version;

    @Relationship(type = "HAS_ATTRIBUTES", direction = OUTGOING)
    private List<ConceptAttributes> attributes = new ArrayList<>();

    @Relationship(type = "AFFECTS", direction = INCOMING)
    private List<BaseRelation> differentials = new ArrayList<>();
}
