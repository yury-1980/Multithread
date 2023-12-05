package ru.clevertec.repository;

import lombok.Builder;
import ru.clevertec.util.impl.RandomUtilImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Builder
public class Server {

    private final Lock lock = new ReentrantLock();
    private final List<Integer> sharedResource = new ArrayList<>();
    private final RandomUtilImpl randomUtilImpl = new RandomUtilImpl();

    /**
     * Добавляет элементы в List, из запроса от Client.
     *
     * @param request Запрос от Client.
     * @return Размер List.
     */
    public Integer processRequest(Request request) {

        try {
            Thread.sleep(randomUtilImpl.getRandomFromOneHundredToThousand());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sharedResource.add(request.value());
        return getSharedResourceSize();
    }

    /**
     * @return Размер List.
     */
    public int getSharedResourceSize() {
        return sharedResource.size();
    }

    /**
     * @return Объект Lock.
     */
    public Lock getLock() {
        return lock;
    }
}
