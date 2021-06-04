package com.canehealth.ckblib.umls.service;

import java.sql.Timestamp;
import java.util.Date;
import com.canehealth.ckblib.library.util.CkblibConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class RestTicketService {
    private String tgt = null;
    private String st;
    private String apikey;
    private String service = "http%3A%2F%2Fumlsks.nlm.nih.gov";
    Timestamp timestamp;
    private WebClient umlsAuthClient;

    public RestTicketService(String apikey){
        this.apikey = apikey;
    }

    public Mono<String> getTgtAsync() {
        this.umlsAuthClient = WebClient.create(CkblibConstants.UMLS_AUTH_URL);
        Mono<String> _tgt = this.umlsAuthClient.post().uri("/cas/v1/tickets")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("apikey", this.apikey))
                .retrieve()
                .bodyToMono(String.class);

        _tgt.subscribe(response -> processTgT(response));
        return _tgt;
    }

    private void processTgT(String response){
        this.tgt = StringUtils.substringBetween(response, "<form action=\"", "\" method=\"POST\">");
        Date date = new Date();
        this.timestamp = new Timestamp(date.getTime());
    }

    private int getElapsedTime(){
        Date date = new Date();
        Timestamp _now = new Timestamp(date.getTime());
        long milliseconds = _now.getTime() - this.timestamp.getTime();
        int seconds = (int) milliseconds / 1000;
        int hours = seconds / 3600;
        return hours;
    }

    public String getTgt(){
        return this.tgt;
    }

}
