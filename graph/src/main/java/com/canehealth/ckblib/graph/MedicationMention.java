package com.canehealth.ckblib.graph;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Data;

@Node("Medication")
@Data
@AllArgsConstructor
public class MedicationMention {
    @Id
    private final String cui;

    private final String name;

    @Version
    private Long version;

    @Relationship(type = "HAS_ATTRIBUTES", direction = OUTGOING)
    private List<ConceptAttributes> attributes = new ArrayList<>();

    // Cannot differentiate drug reactions from treatments
    @Relationship(value = "ASSOCIATED_WITH", direction = INCOMING)
    private final List<BaseRelation> diseases;
}
