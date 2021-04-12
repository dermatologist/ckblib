package com.canehealth.ckblib.service;

import java.util.ArrayList;
import java.util.List;

import com.canehealth.ckblib.model.BaseQuery;
import com.canehealth.ckblib.model.EsearchResultRoot;
import com.canehealth.ckblib.model.PubmedArticleSet;
import com.canehealth.ckblib.util.CkblibConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import lombok.Getter;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class CkbEfetch {

    @Autowired
    private BaseQuery baseQuery;

    @Autowired
    private CkbEsearch ckbEsearch;

    private WebClient webClient;

    List<EsearchResultRoot> esearch_results = new ArrayList<EsearchResultRoot>();

    @Getter
    List<String> results = new ArrayList<String>();

    public CkbEfetch() {
        this.webClient = WebClient.create(CkblibConstants.EFETCH_URL);

    }

    public void setBaseQuery(BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
        ckbEsearch.setBaseQuery(baseQuery);
        ckbEsearch.get().subscribe(esearch_results::add);
    }

    public void get() {
        String query = baseQuery.getFetchQuery(esearch_results.get(0).esearchresult.ids());
        Mono<String> pubmedArticleSet = webClient.get().uri(query).retrieve()
                /*
                 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                 * clientResponse -> Mono.empty())
                 */
                .bodyToMono(String.class);
        pubmedArticleSet.subscribe(results::add);

    }
}
