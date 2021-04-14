package com.canehealth.ckblib.qtakes.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ConceptMention {
    public int begin;
    public int end;
    public String text;
    public int polarity;
    public List<ConceptAttribute> conceptAttributes;
}
