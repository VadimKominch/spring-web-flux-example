package org.example.webflux.service;

import org.example.webflux.model.DataPoint;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Service
public class EventService {

    private DataService dataService;
    private final Random random = new Random();

    public EventService(DataService dataService) {
        this.dataService = dataService;
    }

    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + sequence)
                        .build());
    }

    public Flux<ServerSentEvent<DataPoint>> numberEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<DataPoint>builder()
                        .id(String.valueOf(sequence))
                        .event("number-event")
                        .data(new DataPoint(random.nextInt(100)+10))
                        .build());
    }

    public Flux<ServerSentEvent<String>> mapEvents() {
        return dataService.generateSequence()
                .flatMap(id -> dataService.fetchDetails(id))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + sequence)
                        .build());
    }
}
