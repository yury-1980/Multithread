package ru.clevertec.repository;

import java.util.concurrent.locks.Lock;

public interface Server {

    Integer processRequest(Request request);

    int getSharedResourceSize();

    Lock getLock();
}
