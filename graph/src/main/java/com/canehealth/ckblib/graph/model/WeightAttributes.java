package com.canehealth.ckblib.graph.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class WeightAttributes {

    private List<JournalArticle> confidence;

    private int upVote;

    private int downVote;

}
