package com.example.fortunewheel;

import back.handlers.players.Player;
import javafx.application.Platform;

public class ServerListener {


    public static void listenToServer(Player player, PlayerController controller) {
        new Thread(() -> {
            while (player.getSocket().isConnected()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!player.messagesReceived.isEmpty()) {
                    String message = player.readMessageFromBuffer();
                    System.out.println("Received message from buffer: " + message);
                    Message receivedMessage = Message.convertStringToMessage(message);
                    PlayerMessageProcessor.processMessage(receivedMessage, controller);
                }
            }
        }).start();
    }
}
