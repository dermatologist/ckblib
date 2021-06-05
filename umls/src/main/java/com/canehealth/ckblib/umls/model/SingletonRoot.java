package com.canehealth.ckblib.umls.model;

import java.util.HashMap;
import java.util.List;
import lombok.Data;

@Data
public class SingletonRoot {
    private int pageSize;
    private int pageNumber;
    private int pageCount;
    private List<HashMap<String, Object>> result;
}


