package com.canehealth.ckblib.library.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
@Data
public class PubmedArticleSet {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<PubmedArticle> PubmedArticle;
}

