package ru.clevertec;

import ru.clevertec.repository.Client;
import ru.clevertec.repository.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int n = 100; // Размер списка данных
        int threadCount = 4; // Количество потоков
        Future<Integer> result = null;

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            data.add(i);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Server server = Server.builder()
                .build();
        Client client = Client.builder()
                .data(data)
                .server(server)
                .build();
        MyCallable callable = new MyCallable(client, server);

        for (int i = 0; i < threadCount; i++) {
            result = executorService.submit(callable);
        }

        if (result.get() == n) {
            System.out.println("Колличество данных совподает: "+ result.get() + " = " + n);
        } else {
            System.out.println("Колличество данных не совподает!");
        }

        executorService.shutdown();
        executorService.awaitTermination(1L, TimeUnit.HOURS);

        Integer accumulator = client.getAccumulator();

        int expectedValue = (1 + n) * (n / 2);
        System.out.println("accumulator = " + accumulator + "    " + "expectedValue = " + expectedValue);
        System.out.println("Контроль на стороне клиента: " + (accumulator == expectedValue ? "Пройден" : "Не пройден"));
    }
}