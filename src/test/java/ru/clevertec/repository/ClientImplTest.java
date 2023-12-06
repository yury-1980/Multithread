package ru.clevertec.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientImplTest {

    @Test
    void shouldReturnInputParameter() {
        // given
        int expected = 5;
        Request request = new Request(expected);

        // when
        int actual = request.value();

        // then
        Assertions.assertEquals(expected, actual);
    }
}