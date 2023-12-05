package ru.clevertec.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.MyCallable;
import ru.clevertec.repository.impl.ClientImpl;
import ru.clevertec.repository.impl.ServerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AllTest {

    private int n;
    private Server server;
    private Client client;
    private int threadCount;
    private MyCallable callable;
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        n = 100;
        threadCount = 4;
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            data.add(i);
        }
        executorService = Executors.newFixedThreadPool(threadCount);
        server = ServerImpl.builder()
                .build();
        client = ClientImpl.builder()
                .data(data)
                .server(server)
                .build();
        callable = new MyCallable(client, server);
    }

    @Test
    void shouldReturnTotalSum() {
        // given
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(callable);
        }

        // when
        Integer actual = client.getAccumulator();
        int expected = (1 + n) * (n / 2);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnSizeOfArray() {
        // given
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(callable);
        }

        // when
        int actual = server.getSharedResourceSize();
        int expected = n;

        // then
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void cleanup() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1L, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}