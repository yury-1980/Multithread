package ru.clevertec.repository;

import java.util.concurrent.locks.Lock;

public interface Client {

    int getRequestServer() throws InterruptedException;

    Integer getAccumulator();

    Lock getLock();
}
