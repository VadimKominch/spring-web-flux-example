package org.example.webflux.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class EmployeeControllerTest {
    @Autowired
    private WebTestClient client;

    @Test
    public void returnDataWhenUserHaveAccessToData() {
        client.mutateWith(SecurityMockServerConfigurers.mockUser("user").roles("USER"))
                .get()
                .uri("/employees")
                .exchange() // Trigger the request
                .expectStatus()
                .isOk()
//                .returnResult(String.class)
//                .getResponseBody()// Expect an HTTP 200 OK status
                .expectBody(String.class) // Expect the response body to be a String
                .isEqualTo("Hello,world"); // Assert that the body is "Hello,world"
    }
}
