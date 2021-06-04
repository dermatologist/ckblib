package com.canehealth.ckblib.umls.service;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.canehealth.ckblib.umls.model.SearchRoot;
import com.canehealth.ckblib.umls.model.SingletonRoot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@SpringBootTest
class RestTicketServiceTest {

    @Test
    void shouldSearchAndReturnResults() throws InterruptedException{

        Exception exception = assertThrows(
                org.springframework.web.reactive.function.client.WebClientResponseException.class, () -> {

                    /*
                        USAGE EXAMPLE
                        Constructor gets tgt refreshing token every 6 hours.
                        So reuse the same object.
                    */
                    RestTicketService restTicketService = new RestTicketService("YOURUMLSAPIKEY");
                    Mono<SearchRoot> result = restTicketService.webClient().get()
                            .uri(uriBuilder -> uriBuilder.path("/search/current")
                                    .queryParam("ticket", restTicketService.getSt())
                                    .queryParam("string", "diabetes")
                                    .queryParam("searchType", "exact").build())
                            .retrieve().bodyToMono(SearchRoot.class);
                    /*
                     * <-- WITH URL PARAMS --> this.webClient.get() .uri(uriBuilder - > uriBuilder
                     * .path("/products/{id}/attributes/{attributeId}") .build(2, 13)) .retrieve();
                     */
                    System.out.println(result.block());
        });
        String expectedMessage = "401 Unauthorized from POST https://utslogin.nlm.nih.gov/cas/v1/tickets";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void shouldRetrieveCui() throws InterruptedException {

        Exception exception = assertThrows(
                org.springframework.web.reactive.function.client.WebClientResponseException.class,
                () -> {

                    // * USAGE EXAMPLE with QueryParams
                    RestTicketService restTicketService = new RestTicketService("YOURUMLSAPIKEY");
                    Mono<SingletonRoot> result = restTicketService.webClient().get()
                            .uri(uriBuilder -> uriBuilder.path("/content/current/CUI/{cui}/atoms")
                                    .queryParam("ticket", restTicketService.getSt())
                                    .queryParam("searchType", "exact").build("C0155502"))
                            .retrieve().bodyToMono(SingletonRoot.class);
                    /*
                     * <-- WITH URL PARAMS --> this.webClient.get() .uri(uriBuilder - > uriBuilder
                     * .path("/products/{id}/attributes/{attributeId}") .build(2, 13)) .retrieve();
                     */
                    System.out.println(result.block());
                });
        String expectedMessage =
                "401 Unauthorized from POST https://utslogin.nlm.nih.gov/cas/v1/tickets";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}