package com.canehealth.ckblib.service;

import com.canehealth.ckblib.model.BaseQuery;
import com.canehealth.ckblib.util.CkblibConstants;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class CkbEsearch {

    private final BaseQuery baseQuery;

    WebClient client;


    public CkbEsearch(BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
        this.client = WebClient.create(CkblibConstants.ESEARCH_URL);
    }

    public String get() {

        return "Hello";
    }
}
