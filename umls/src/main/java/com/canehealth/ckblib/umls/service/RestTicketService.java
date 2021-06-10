/**
 * Copyright (c) 2021 Bell Eapen
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
    private String apikey;
    private String service = "http://umlsks.nlm.nih.gov";
    Timestamp timestamp;

    /** Constructor sets the TGT token
     * @since 0.16.0
     * @param apikey
     */
    public RestTicketService(String apikey){
        this.apikey = apikey;
        processTgT(getTgtAsync().block()); // Get a TGT on creation
    }

    public Mono<String> getTgtAsync() {
        WebClient client = WebClient.create(CkblibConstants.UMLS_AUTH_URL);
        Mono<String> _tgt = client.post().uri("/cas/v1/tickets")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("apikey", this.apikey))
                .retrieve()
                .bodyToMono(String.class);

        _tgt.subscribe(response -> processTgT(response));
        return _tgt;
    }

    public Mono<String> getStAsync() {
        if(this.getElapsedTime() > 6) // if token is older than 6 hours (valid till 8 hrs), get a new one
            getTgtAsync().block();
        WebClient client = WebClient.create(this.tgt);
        Mono<String> _st = client.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("service", this.service)).retrieve()
                .bodyToMono(String.class);
        return _st;
    }

    /** Returns WebClient with the default baseUrl and headers
     * @since 0.16.0
     * @return client
     */
    public WebClient webClient(){
        WebClient client = WebClient.builder().baseUrl("https://uts-ws.nlm.nih.gov/rest")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                // .defaultUriVariables(Collections.singletonMap("ticket", getStAsync().block()))
                .build();
        return client;
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

    /**
     * @since 0.16.0
     * @return St token
     */
    public String getSt(){
        return getStAsync().block();
    }

}
