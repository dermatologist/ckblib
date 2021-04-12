package com.canehealth.ckblib.model;

import java.util.List;

public class Esearchresult {
    public String count;
    public String retmax;
    public String retstart;
    public String querykey;
    public String webenv;
    public List<String> idlist;
    public List<Translationset> translationset;
    public List<Object> translationstack;
    public String querytranslation;

    public String ids() {
        return String.join(",", idlist);
    }
}
