package com.canehealth.ckblib.qtakes.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ConceptAttribute {
    public String code;
    public String cui;
    public String codingScheme;
    public String tui;
}
