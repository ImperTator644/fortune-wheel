package com.fortunewheel.backend;

import com.fortunewheel.backend.handlers.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class ServerMain implements MainInterface{
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            Server server = new Server(serverSocket, Executors.newFixedThreadPool(ROOM_COUNT));
            server.startServer();
        } catch (IOException e) {
            System.out.println("Problem creating a server " + e.getMessage());
        }
    }
}
