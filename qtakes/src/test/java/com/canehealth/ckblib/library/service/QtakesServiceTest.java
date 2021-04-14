package com.canehealth.ckblib.library.service;

import java.util.concurrent.TimeUnit;

import com.canehealth.ckblib.qtakes.model.QtakesRoot;
import com.canehealth.ckblib.qtakes.service.QtakesService;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpClassCallback.callback;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;
import org.mockserver.model.Header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class QtakesServiceTest {

    @Autowired
    private QtakesService qtakesService;

    private static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @After
    public static void stopServer() {
        mockServer.stop();
    }

    private void createExpectation() {
        mockServer = startClientAndServer(18093);
        new MockServerClient("127.0.0.1", 18093)
                .when(request().withMethod("POST").withPath("/analyze").withHeader("\"Content-type\",\"text/plain\""))
                .respond(response().withStatusCode(200)
                        .withHeaders(new Header("Content-Type", "text/plain; charset=utf-8"))
                        .withBody(
                                "{\"AnatomicalSiteMention\":[{\"begin\":73,\"end\":77,\"text\":\"face\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"89545001\",\"cui\":\"C0015450\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T029\"}]},{\"begin\":91,\"end\":95,\"text\":\"oral\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"74262004\",\"cui\":\"C0226896\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T030\"}]}],\"MedicationMention\":[{\"begin\":91,\"end\":95,\"text\":\"oral\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"385268001\",\"cui\":\"C1272919\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T122\"}]},{\"begin\":96,\"end\":107,\"text\":\"paracetamol\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"387517004\",\"cui\":\"C0000970\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T109\"},{\"code\":\"90332006\",\"cui\":\"C0000970\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T121\"},{\"code\":\"90332006\",\"cui\":\"C0000970\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T109\"},{\"code\":\"161\",\"cui\":\"C0000970\",\"codingScheme\":\"RXNORM\",\"tui\":\"T121\"},{\"code\":\"161\",\"cui\":\"C0000970\",\"codingScheme\":\"RXNORM\",\"tui\":\"T109\"},{\"code\":\"387517004\",\"cui\":\"C0000970\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T121\"}]}],\"DrugChangeStatusAnnotation\":[],\"StrengthAnnotation\":[],\"FractionStrengthAnnotation\":[],\"FrequencyUnitAnnotation\":[],\"DiseaseDisorderMention\":[{\"begin\":12,\"end\":24,\"text\":\"hypertension\",\"polarity\":-1,\"conceptAttributes\":[{\"code\":\"38341003\",\"cui\":\"C0020538\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T047\"}]},{\"begin\":30,\"end\":38,\"text\":\"diabetes\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"73211009\",\"cui\":\"C0011849\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T047\"}]},{\"begin\":57,\"end\":65,\"text\":\"erythema\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"247441003\",\"cui\":\"C0041834\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T047\"},{\"code\":\"444827008\",\"cui\":\"C0041834\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T047\"},{\"code\":\"70819003\",\"cui\":\"C0041834\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T047\"},{\"code\":\"86735004\",\"cui\":\"C0041834\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T047\"}]}],\"SignSymptomMention\":[{\"begin\":44,\"end\":52,\"text\":\"pruritus\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"279333002\",\"cui\":\"C0033774\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T184\"},{\"code\":\"424492005\",\"cui\":\"C0033774\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T184\"},{\"code\":\"418363000\",\"cui\":\"C0033774\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T184\"},{\"code\":\"418290006\",\"cui\":\"C0033774\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T184\"}]}],\"DateAnnotation\":[],\"RouteAnnotation\":[],\"MeasurementAnnotation\":[],\"ProcedureMention\":[{\"begin\":112,\"end\":126,\"text\":\"blepharoplasty\",\"polarity\":0,\"conceptAttributes\":[{\"code\":\"398248006\",\"cui\":\"C0197213\",\"codingScheme\":\"SNOMEDCT_US\",\"tui\":\"T061\"}]}],\"TimeMention\":[],\"StrengthUnitAnnotation\":[]}")
                        .withDelay(TimeUnit.SECONDS, 1));
    }

    @Test
    public void QtakesServiceTestGet() throws InterruptedException {
        createExpectation();
        qtakesService.setQtakesUrl("http://127.0.0.1:18093");
        qtakesService.post(
                "Lichen planus presented with pruritus and erythema on the face treated with oral paracetamol and blepharoplasty in the morning");
        TimeUnit.SECONDS.sleep(3);
        QtakesRoot r = qtakesService.getQtakesResults();
        assertEquals("face", r.anatomicalSiteMention.get(0).text);
    }

    @SpringBootApplication
    @Configuration
    @ComponentScan(basePackages = "com.canehealth.ckblib")
    static class TestConfiguration {
    }

}
