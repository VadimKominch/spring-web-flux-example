package org.example.webflux.client;

import org.example.webflux.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UsersClient {

    private final WebClient webClient;

    public UsersClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8089").build();
    }

    public Mono<User> getUser(int id) {
        return webClient.get()
                .uri("/user/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Flux<User> fetchUsers(List<Integer> userIds) {
        return Flux.fromIterable(userIds)
                .flatMap(this::getUser);
    }

    public Mono<User> getOtherUser(int id) {
        return webClient.get()
                .uri("/otheruser/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }
}
