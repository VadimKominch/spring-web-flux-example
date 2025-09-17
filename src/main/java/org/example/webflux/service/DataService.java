package org.example.webflux.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class DataService {
    public Flux<Long> generateSequence() {
        return Flux.interval(Duration.ofSeconds(1)).take(5); // 5 items for demo
    }

    // Another service that fetches additional info for an ID
    public Mono<String> fetchDetails(Long id) {
        return Mono.just("Details-for-" + id);
    }
}
