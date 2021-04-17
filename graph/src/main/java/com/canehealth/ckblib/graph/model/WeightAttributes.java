package com.canehealth.ckblib.graph.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeightAttributes {

    // private List<JournalArticle> confidence;

    private Long confidence;

    private Long upVote;

    private Long downVote;

}
