package com.canehealth.ckblib.graph.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeightAttributes {

    private List<JournalArticle> confidence;

    private int upVote;

    private int downVote;

}
