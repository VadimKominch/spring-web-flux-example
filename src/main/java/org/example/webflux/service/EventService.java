package org.example.webflux.service;

import org.example.webflux.model.DataPoint;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@Service
public class EventService {

    public static final int DOTS_COUNT = 500;
    private final DataService dataService;
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
                        .data(new DataPoint(getDot(sequence.intValue())))
                        .build());
    }
    /*
     * harmonic oscilator
     * */
    private double getDot(int order) {
        double t = (double) order / DOTS_COUNT; // условное время
        double modulator = Math.sin(2 * Math.PI * 2 * t);
        return Math.sin(2 * Math.PI * 5 * t + 2 * modulator);
    }

    public Flux<ServerSentEvent<String>> mapEvents() {
        return dataService.generateSequence()
                .flatMap(dataService::fetchDetails)
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + sequence)
                        .build());
    }
}
