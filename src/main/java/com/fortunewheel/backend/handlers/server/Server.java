package com.fortunewheel.backend.handlers.server;

import com.fortunewheel.backend.handlers.server.room.PlayersRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PLAYERS_COUNT = 3;

    public Server(ServerSocket serverSocket, ExecutorService executorService) {
        this.serverSocket = serverSocket;
        this.executorService = executorService;
    }

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    public void startServer(){
        System.out.println("Server started");
        try {
            while (!serverSocket.isClosed()) {
                List<Socket> playersList = new ArrayList<>(3);
                while(playersList.size() < 3) {
                    Socket socket = serverSocket.accept();
                    playersList.add(socket);
                    System.out.println("New player has connected");
                }

                PlayersRoom playersRoom = new PlayersRoom(playersList, Executors.newFixedThreadPool(PLAYERS_COUNT));
                executorService.execute(playersRoom);
                System.out.println("Executor started room " + playersRoom);
            }
        }
        catch (IOException ioException){
            System.out.println("Error creating a connection with client " + ioException.getMessage());
            closeEverything();
        }
    }

    private void closeEverything() {
        executorService.shutdown();
        try{
            serverSocket.close();
        }
        catch (IOException ioException){
            System.out.println("Error closing server socket " + ioException.getMessage());
        }
    }
}
