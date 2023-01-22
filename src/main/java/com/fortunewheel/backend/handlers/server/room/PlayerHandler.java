package com.fortunewheel.backend.handlers.server.room;


import com.fortunewheel.backend.handlers.Handler;
import com.fortunewheel.backend.handlers.players.PlayerModel;

import java.io.IOException;
import java.net.Socket;

public class PlayerHandler extends Handler implements Runnable {
    private PlayerModel playerModel;

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public PlayerHandler(Socket socket) {
        super(socket);
        playerModel = new PlayerModel(getUserName());
        System.out.println("Player handler initialized");
    }

    @Override
    public void run() {
        while (socket.isConnected()) {

        }
        closeEverything();
    }

    private String getUserName() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            System.out.println("Error receiving message on the server");
            closeEverything();
        }
        return "default";
    }

    public String getMessageFromClient() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            System.out.println("Error receiving message on the server");
            closeEverything();
        }
        return null;
    }

    public void sendMessageToClient(String message) {
        try {
            this.getWriter().write(message);
            this.getWriter().newLine();
            this.getWriter().flush();
        } catch (IOException e) {
            System.out.println("Error sending message from server to player");
            closeEverything();
        }
    }
}
