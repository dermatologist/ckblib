package com.canehealth.ckblib.service;

import java.util.ArrayList;
import java.util.List;

import com.canehealth.ckblib.model.BaseQuery;
import com.canehealth.ckblib.model.EsearchResultRoot;
import com.canehealth.ckblib.util.CkblibConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class CkbEsearch {

    @Autowired
    @Setter
    private BaseQuery baseQuery;

    private WebClient webClient;

    @Getter
    List<EsearchResultRoot> results = new ArrayList<EsearchResultRoot>();

    public CkbEsearch() {
        this.webClient = WebClient.create(CkblibConstants.ESEARCH_URL);
    }

    public Mono<EsearchResultRoot> get() {
        Mono<EsearchResultRoot> esearchResultRoot = webClient.get().uri(baseQuery.getQuery()).retrieve()
                /*
                 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                 * clientResponse -> Mono.empty())
                 */
                .bodyToMono(EsearchResultRoot.class);

        esearchResultRoot.subscribe(results::add);
        return esearchResultRoot;
    }
}
