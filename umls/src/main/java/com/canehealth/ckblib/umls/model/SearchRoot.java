package com.canehealth.ckblib.umls.model;

import lombok.Data;

@Data
public class SearchRoot {
    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private ResultRoot result;
}



