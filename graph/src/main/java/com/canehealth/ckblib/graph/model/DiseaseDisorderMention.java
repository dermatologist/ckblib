package com.canehealth.ckblib.graph.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Node("Disease")
@AllArgsConstructor
public class DiseaseDisorderMention {

    @Id
    private final String cui;

    @Getter
    private final String name;

    @Getter
    private int support;

    @Relationship(type = "HAS_ATTRIBUTES", direction = OUTGOING)
    private List<ConceptAttributes> attributes = new ArrayList<>();

    @Relationship(type = "HAS_SYMPTOM", direction = OUTGOING)
    private Map<SignSymptomMention, WeightAttributes> symptoms = new HashMap<>();

    @Relationship(type = "HAS_ANATOMY", direction = OUTGOING)
    private Map<AnatomicalSiteMention, WeightAttributes> anatomy = new HashMap<>();

    @Relationship(type = "HAS_TREATMENT", direction = OUTGOING)
    private Map<MedicationMention, WeightAttributes> treatments = new HashMap<>();

    @Relationship(type = "HAS_PROCEDURE", direction = OUTGOING)
    private Map<ProcedureMention, WeightAttributes> procedures = new HashMap<>();
}
