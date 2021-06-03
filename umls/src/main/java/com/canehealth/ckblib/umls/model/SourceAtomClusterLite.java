package com.canehealth.ckblib.umls.model;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class SourceAtomClusterLite {
    private String ui;
    private String name;
    private boolean obsolete;
    private boolean suppressible;
    private String rootSource;
    private int cVMemberCount;
    private int atomCount;
    private String concepts;
    private String atoms;
    private String parents;
    private String children;
    private String descendants;
    private String ancestors;
    private String relations;
    private String definitions;
    private String attributes;
    private String defaultPreferredAtom;
    private List<HashMap<String, Object>> subsetMemberships;
    private List<HashMap<String, Object>> contentViewMemberships;
}
