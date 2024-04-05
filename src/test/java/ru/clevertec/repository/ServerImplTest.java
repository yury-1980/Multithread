package ru.clevertec.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.repository.impl.ServerImpl;

import java.util.ArrayList;
import java.util.List;

class ServerImplTest {

    private int dataSize;
    private Server server;
    private List<Integer> data;

    @BeforeEach
    void setUp() {
        dataSize = 100;
        data = new ArrayList<>();
        for (int i = 1; i <= dataSize; i++) {
            data.add(i);
        }
        server = ServerImpl.builder()
                .build();
    }

    @Test
    void shouldReturnGeneralSizeInputData() {
        // given
        int actual = 0;
        int expected = dataSize;

        // when
        for (Integer i : data) {
            actual = server.processRequest(new Request(i));
        }

        // then
        Assertions.assertEquals(expected, actual);
    }
}