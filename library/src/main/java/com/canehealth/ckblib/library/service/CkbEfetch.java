package com.canehealth.ckblib.library.service;

import java.util.ArrayList;
import java.util.List;

import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.model.EsearchResultRoot;
import com.canehealth.ckblib.library.util.CkbXpath;
import com.canehealth.ckblib.library.util.CkblibConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

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

    public Mono<String> getChain(BaseQuery baseQuery) {
        this.baseQuery = baseQuery;
        ckbEsearch.setBaseQuery(baseQuery);
        return ckbEsearch.get().flatMap(x -> webClient.get().uri(baseQuery
                .getFetchQuery(x.esearchresult.ids())).retrieve().bodyToMono(String.class));
    }

    public Mono<String> get() {
        String query = baseQuery.getFetchQuery(esearch_results.get(0).esearchresult.ids());
        Mono<String> pubmedArticleSet = webClient.get().uri(query).retrieve()
                /*
                 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                 * clientResponse -> Mono.empty())
                 */
                .bodyToMono(String.class);
        pubmedArticleSet.subscribe(results::add);
        return pubmedArticleSet;
    }

    public List<String> getPath(String xmlPath) {
        List<String> output = new ArrayList<>();
        if (!results.isEmpty()) {
            CkbXpath ckbXpath = new CkbXpath(results.get(0));
            NodeList nodeList = ckbXpath.getNodes(xmlPath);
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);
                output.add(node.getTextContent());
            }
        }
        return output;
    }

    public List<String> getPathFromString(String xmlPath, String xmlString) {
        List<String> output = new ArrayList<>();
        CkbXpath ckbXpath = new CkbXpath(xmlString);
        NodeList nodeList = ckbXpath.getNodes(xmlPath);
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            output.add(node.getTextContent());
        }
        return output;
    }
}
