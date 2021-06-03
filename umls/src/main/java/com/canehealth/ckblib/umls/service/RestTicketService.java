package com.canehealth.ckblib.umls.service;

import java.sql.Timestamp;
import java.util.Date;
import com.canehealth.ckblib.library.util.CkblibConstants;
import com.canehealth.ckblib.umls.model.PostModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class RestTicketService {
    private String tgt = null;
    private String st;
    private PostModel postModel = new PostModel();
    Timestamp timestamp;
    private WebClient umlsServiceClient;
    private WebClient umlsAuthClient;

    public RestTicketService(String apikey){
        this.postModel.setApikey(apikey);
        this.umlsAuthClient = WebClient.create(CkblibConstants.UMLS_AUTH_URL);
        this.umlsServiceClient = WebClient.create(CkblibConstants.UMLS_SERVICE_URL);
    }

    public Mono<String> getTgt() {
        Mono<String> _tgt = this.umlsAuthClient.post().uri("/cas/v1/tickets")
                .body(Mono.just(this.postModel), PostModel.class)
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

}
