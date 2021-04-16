package com.canehealth.ckblib.graph.model;

import java.util.List;


import lombok.Data;

@Data
public class WeightAttributes {

    private List<JournalArticle> confidence;

    private int upVote;

    private int downVote;

}
