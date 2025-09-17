package org.example.webflux.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {

    private EventService eventService;
    private DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = Mockito.mock(DataService.class);
        eventService = new EventService(dataService);
    }

    @Test
    public void produceThreeEventsWithStrictContent() {

        Flux<ServerSentEvent<String>> eventStream = eventService.streamEvents();

        StepVerifier
                .create(eventStream)
                .assertNext(event -> {
                    // First element
                    assertEquals("0", event.id());
                    assertEquals("periodic-event", event.event());
                    assertEquals("SSE - 0", event.data());
                })
                .assertNext(event -> {
                    // Second element
                    assertEquals("1", event.id());
                    assertEquals("periodic-event", event.event());
                    assertEquals("SSE - 1", event.data());
                })
                .assertNext(event -> {
                    // Third element
                    assertEquals("2", event.id());
                    assertEquals("periodic-event", event.event());
                    assertEquals("SSE - 2", event.data());
                })
                .thenCancel()
                .verify();
    }
}
