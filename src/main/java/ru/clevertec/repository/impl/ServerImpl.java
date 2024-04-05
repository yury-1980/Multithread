package ru.clevertec.repository.impl;

import lombok.Builder;
import ru.clevertec.repository.Request;
import ru.clevertec.repository.Server;
import ru.clevertec.util.ConstTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Builder
public class ServerImpl implements Server {

    private final Lock lock = new ReentrantLock();
    private final List<Integer> sharedResource = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Добавляет элементы в List, из запроса от Client.
     *
     * @param request Запрос от Client.
     * @return Размер List.
     */
    @Override
    public Integer processRequest(Request request) {

        try {
            Thread.sleep(new Random().nextInt(ConstTime.ONE_HUNDRED, ConstTime.THOUSAND));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sharedResource.add(request.value());
        return getSharedResourceSize();
    }

    /**
     * @return Размер List.
     */
    @Override
    public int getSharedResourceSize() {
        return sharedResource.size();
    }

    /**
     * @return Объект Lock.
     */
    @Override
    public Lock getLock() {
        return lock;
    }
}
