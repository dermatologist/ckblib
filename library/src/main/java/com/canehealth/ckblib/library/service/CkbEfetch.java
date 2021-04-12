package com.canehealth.ckblib.library.service;

import java.util.ArrayList;
import java.util.List;

import com.canehealth.ckblib.library.model.BaseQuery;
import com.canehealth.ckblib.library.model.EsearchResultRoot;
import com.canehealth.ckblib.library.model.PubmedArticleSet;
import com.canehealth.ckblib.library.util.CkblibConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public PubmedArticleSet getPubmedArticleSet() {
        // If you use the useWrapping option globally
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        PubmedArticleSet pubmedArticleSet = new PubmedArticleSet();
        try {
            pubmedArticleSet = xmlMapper.readValue(results.get(0).replaceAll("<AbstractText ", "<AbstractText>"),
                    PubmedArticleSet.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pubmedArticleSet;
    }
}
