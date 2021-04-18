package com.canehealth.ckblib.graph.model;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node("Medication")
@Data
public class MedicationMention {
    @Id
    private String cui;

    private String name;

    @Version
    private Long version;

    @Relationship(type = "HAS_ATTRIBUTES", direction = OUTGOING)
    private List<ConceptAttributes> attributes = new ArrayList<>();

    // Cannot differentiate drug reactions from treatments
    @Relationship(type = "ASSOCIATION_OF", direction = OUTGOING)
    private List<BaseRelation> diseases = new ArrayList<>();
}
