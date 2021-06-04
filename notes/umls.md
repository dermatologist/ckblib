
* https://stackoverflow.com/questions/59792224/how-to-post-request-with-spring-boot-web-client-for-form-data-for-content-type-a
```
webClient client = WebClient.builder()
        .baseUrl("SOME-BASE-URL")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build();

return client.post()
        .uri("SOME-URI)
        .body(BodyInserters.fromFormData("username", "SOME-USERNAME")
                .with("password", "SONE-PASSWORD"))
                .retrieve()
                .bodyToFlux(SomeClass.class)
                .onErrorMap(e -> new MyException("messahe",e))
        .blockLast();
```

* https://howtodoinjava.com/spring-webflux/webclient-get-post-example/
* https://www.baeldung.com/spring-5-webclient