package com.canehealth.ckblib.umls.model;

import java.util.List;
import lombok.Data;

@Data
public class ResultRoot {
    private String classType;
    private boolean sourceOriginated;
    private String rootSource;
    private String value;
    private List<SearchResult> results;

    private String ui;

    private boolean suppressible;

    private boolean obsolete;

    private int atomCount;

    private int cVMemberCount;

    private String attributes;

    private String atoms;

    private String descendants;

    private String ancestors;

    private String parents;

    private String children;

    private String relations;

    private String definitions;

    private String concepts;

    private String defaultPreferredAtom;

    private List<SubsetMemberships> subsetMemberships;

    private List<ContentViewMemberships> contentViewMemberships;

    private String name;


}
