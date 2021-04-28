package com.canehealth.ckblib.qtakes.service;

import java.util.ArrayList;
import java.util.List;

import com.canehealth.ckblib.library.service.ServiceProperties;
import com.canehealth.ckblib.qtakes.model.QtakesRoot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Getter;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class QtakesService {

    private WebClient webClient;

    @Getter
    private String qtakesUrl = "";

    @Getter
    List<String> results = new ArrayList<String>();

    public QtakesService() {
    }

    public void setQtakesUrl(String qtakesUrl) {
        if (!qtakesUrl.equals(""))
            this.webClient = WebClient.create(qtakesUrl);
        this.qtakesUrl = qtakesUrl;
    }

    public void clearResults() {
        this.results = new ArrayList<String>();
    }

    public Mono<String> post(String queryString) {
        Mono<String> qtakesRoot = webClient.post().uri("/analyze")
                // .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                .body(Mono.just(queryString), String.class).retrieve().bodyToMono(String.class);

        qtakesRoot.subscribe(results::add);
        return qtakesRoot;
    }

    public QtakesRoot getQtakesResults() {
        try {
            return new ObjectMapper().readValue(results.get(0), QtakesRoot.class);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new QtakesRoot();
    }

}
