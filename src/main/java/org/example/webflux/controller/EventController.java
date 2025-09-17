package org.example.webflux.controller;

import org.example.webflux.model.DataPoint;
import org.example.webflux.service.EventService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @CrossOrigin(origins = "*")
//    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamEvents() {
        return eventService.streamEvents();
    }
/**
 * can emulated with synchronous api using @see SseEmitter, additionally need to add executor service to work properly
 * */
    @CrossOrigin(origins = "*")
//    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/data", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<DataPoint>> numberEvents() {
        return eventService.numberEvents();
    }

    @GetMapping("/merge")
    public Flux<ServerSentEvent<String>> mergeEvents() {
        return eventService.mapEvents();
    }
}
