package com.canehealth.ckblib.library.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
public class Abstract {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<String> AbstractText;
    String CopyrightInformation;
}
