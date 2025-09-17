package org.example.webflux.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class EventsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void receiveThreeEvents() {

        Flux<ServerSentEvent<String>> eventFlux = webTestClient.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<ServerSentEvent<String>>() {})
                .getResponseBody();

                StepVerifier
                    .create(eventFlux)
                    .assertNext(event -> {
                        Assertions.assertEquals("periodic-event",event.event());
                        Assertions.assertTrue(event.data().toString().startsWith("SSE - "));
                    })
                    .expectNextCount(3)  // check first 3 events
                    .thenCancel()
                    .verify();
    }

}
