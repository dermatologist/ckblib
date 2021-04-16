package com.canehealth.ckblib.graph;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class WeightAttributes {

    private List<JournalArticle> confidence;

    private int upVote;

    private int downVote;

}
