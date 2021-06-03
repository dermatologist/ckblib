package com.canehealth.ckblib.umls.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PostModel {
    private String apikey;
    private String service = "http%3A%2F%2Fumlsks.nlm.nih.gov";
}
