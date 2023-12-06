package ru.clevertec;

import lombok.AllArgsConstructor;
import ru.clevertec.repository.Client;
import ru.clevertec.repository.Server;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class MyCallable implements Callable<Integer> {

    private Client client;
    private Server server;

    @Override
    public Integer call() {
        int requestServer;

        try {
            lockClientServer(client, server);
            requestServer = client.getRequestServer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            client.getLock().unlock();
            server.getLock().unlock();
        }

        return requestServer;
    }

    public void lockClientServer(Client client, Server server) {
        while (true) {
            boolean clientLock = client.getLock().tryLock();
            boolean serverLock = server.getLock().tryLock();

            if (clientLock && serverLock) {
                break;
            }

            if (clientLock) {
                client.getLock().unlock();
            }

            if (serverLock) {
                server.getLock().unlock();
            }
        }
    }
}
