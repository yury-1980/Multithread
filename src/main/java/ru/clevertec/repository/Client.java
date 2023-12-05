package ru.clevertec.repository;

import lombok.Builder;
import ru.clevertec.util.RandomUtil;
import ru.clevertec.util.impl.RandomUtilImpl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Builder
public class Client {

    private final Lock lock = new ReentrantLock();
    private final List<Integer> data;
    private final Server server;
    private final RandomUtil randomUtil = new RandomUtilImpl();
    private final Random random = new Random();
    private int accumulator;

    /**
     * Отправляет запрос серверу, и получает ответ.
     *
     * @return Текущий размер созданного массива на сервере.
     * @throws InterruptedException
     */
    public int getRequestServer() throws InterruptedException {
        while (!data.isEmpty()) {
            int index = random.nextInt(data.size());
            int value = data.remove(index);
            Request request = new Request(value);
            Thread.sleep(randomUtil.getRandomFromOneHundredToFiveHundred());
            accumulator += server.processRequest(request);
        }
        return server.getSharedResourceSize();
    }

    /**
     * Суммирует в переменную ответы от сервера.
     *
     * @return Сумма ответов сервера.
     */
    public Integer getAccumulator() {
        return accumulator;
    }

    /**
     * @return Объект Lock.
     */
    public Lock getLock() {
        return lock;
    }
}