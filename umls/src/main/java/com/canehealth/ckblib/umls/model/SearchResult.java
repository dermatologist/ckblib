package com.canehealth.ckblib.umls.model;

import lombok.Data;

@Data
public class SearchResult {
    private String ui;
    private String name;
    private String uri;
    private String rootSource;
}
