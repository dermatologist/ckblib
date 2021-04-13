package com.canehealth.ckblib.library.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class PubmedArticle {
    @JacksonXmlElementWrapper(useWrapping = false)
    MedlineCitation MedlineCitation;
    @JacksonXmlElementWrapper(useWrapping = false)
    PubmedData PubmedData;
}

