package com.canehealth.ckblib.umls.model;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class ConceptLite {
    private String ui;
    private String name;
    private List<HashMap<String, Object>> semanticTypes;
    private int atomCount;
    private String atoms;
    private String relations;
    private String definitions;
    private String defaultPreferredAtom;
}
