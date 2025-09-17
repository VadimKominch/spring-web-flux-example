package org.example.webflux;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.example.webflux.client.UsersClient;
import org.example.webflux.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@WireMockTest(httpPort = 8089)
public class UsersClientTest {

    private final UsersClient client = new UsersClient(WebClient.builder());

    @Test
    public void givenClient_whenFetchingUsers_thenExecutionTimeIsLessThanDouble() {

        int requestsNumber = 5;
        int singleRequestTime = 1000;

        for (int i = 1; i <= requestsNumber; i++) {
            stubFor(get(urlEqualTo("/user/" + i)).willReturn(aResponse().withFixedDelay(singleRequestTime)
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(String.format("{ \"id\": %d }", i))));
        }

        List<Integer> userIds = IntStream.rangeClosed(1, requestsNumber)
                .boxed()
                .collect(Collectors.toList());

        long start = System.currentTimeMillis();
        List<User> users = client.fetchUsers(userIds).collectList().block();
        long end = System.currentTimeMillis();

        long totalExecutionTime = end - start;

        assertEquals("Unexpected number of users", requestsNumber, users.size());
        assertTrue("Execution time is too big", 2 * singleRequestTime > totalExecutionTime);
    }
}
