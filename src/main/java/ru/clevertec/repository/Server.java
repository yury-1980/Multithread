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

    public Integer processRequest(Request request) {

        try {
            Thread.sleep(randomUtilImpl.getRandomFromOneHundredToThousand());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sharedResource.add(request.value());
        return getSharedResourceSize();
    }

    public int getSharedResourceSize() {
        return sharedResource.size();
    }

    public Lock getLock() {
        return lock;
    }
}
