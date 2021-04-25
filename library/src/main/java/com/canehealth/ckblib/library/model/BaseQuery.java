package com.canehealth.ckblib.library.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class BaseQuery {

    private String db = "pubmed";
    private String term = "Eapen BR";
    private int retmax = 10;
    private int reldate = 0;
    private int retstart = 0;
    private String api_key = "";

    public String getQuery() {
        String query = "?retmode=json&db=" + db + "&term=" + term + "&retmax=" + Integer.toString(retmax);
        if (reldate > 0) {
            query += "&reldate=" + Integer.toString(reldate);
        }
        if (retstart > 0) {
            query += "&retstart=" + Integer.toString(retstart);
        }
        if(!"".equals(api_key)){
            query += "&api_key=" + api_key;
        }
        return query.replace(" ", "+");
    }

    public String getFetchQuery(String ids) {
        String query = "?retmode=xml&rettype=abstract&db=" + db + "&id=" + ids;
        return query.replace(" ", "+");
    }
}
