package com.canehealth.ckblib.library.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
public class Article {
    @JacksonXmlElementWrapper(useWrapping = false)
    Journal Journal;
    @JacksonXmlElementWrapper(useWrapping = false)
    String ArticleTitle;
    Pagination Pagination;
    ELocationID ELocationID;
    @JacksonXmlElementWrapper(useWrapping = false)
    Abstract Abstract;
    @JacksonXmlElementWrapper(useWrapping = false)
    AuthorList AuthorList;
    String Language;
    @JacksonXmlElementWrapper(useWrapping = false)
    PublicationTypeList PublicationTypeList;
    @JacksonXmlElementWrapper(useWrapping = false)
    ArticleDate ArticleDate;
    String PubModel;
    String text;
}
