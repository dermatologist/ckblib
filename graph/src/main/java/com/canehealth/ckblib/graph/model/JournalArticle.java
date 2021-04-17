package com.canehealth.ckblib.graph.model;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Data;

@Node("Article")
@Data
@AllArgsConstructor
public class JournalArticle {

    @Id
    private String pmid;

    @Version
    private Long version;

    public String name;

    @Relationship(type = "EVIDENCE", direction = INCOMING)
    private List<BaseRelation> diseases = new ArrayList<>();
}
