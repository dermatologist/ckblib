package com.canehealth.ckblib.graph.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.stereotype.Component;

import lombok.Data;

@Node("Article")
@Data
@Component
public class JournalArticle {

    @Id
    private String pmid;

    @Version
    private Long version;

    public String name;

    // @Relationship(type = "EVIDENCE_OF", direction = OUTGOING)
    // private List<BaseRelation> diseases = new ArrayList<>();
}
