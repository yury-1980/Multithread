package ru.clevertec;

import ru.clevertec.repository.impl.ClientImpl;
import ru.clevertec.repository.impl.ServerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int dataSize = 100;
        int threadCount = 4;
        Future<Integer> result = null;

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= dataSize; i++) {
            data.add(i);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        ServerImpl serverImpl = ServerImpl.builder()
                .build();
        ClientImpl clientImpl = ClientImpl.builder()
                .data(data)
                .server(serverImpl)
                .build();
        MyCallable callable = new MyCallable(clientImpl, serverImpl);

        for (int i = 0; i < threadCount; i++) {
            result = executorService.submit(callable);
        }

        if (result.get() == dataSize) {
            System.out.println("Колличество данных совподает: " + result.get() + " = " + dataSize);
        } else {
            System.out.println("Колличество данных не совподает!");
        }

        executorService.shutdown();
        executorService.awaitTermination(1L, TimeUnit.HOURS);

        Integer accumulator = clientImpl.getAccumulator();

        int expectedValue = (1 + dataSize) * (dataSize / 2);
        System.out.println("accumulator = " + accumulator + "    " + "expectedValue = " + expectedValue);
        System.out.println("Контроль на стороне клиента: " + (accumulator == expectedValue ? "Пройден" : "Не пройден"));
    }
}