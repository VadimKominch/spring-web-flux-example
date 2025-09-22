package org.example.webflux.blockhound;

import org.junit.jupiter.api.Test;
import reactor.blockhound.BlockHound;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BlockHoundTest {

    @Test
    public void simpleTest() {
        BlockHound.install();

        Mono<Long> mono = Mono.delay(Duration.ofSeconds(1))
                .doOnNext(it -> {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        assertThrows(RuntimeException.class, mono::block);
    }
}
