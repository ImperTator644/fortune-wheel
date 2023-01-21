package com.example.fortunewheel;

import back.game.Functions;
import back.handlers.players.Player;

public class PlayerMessageSender {
    public static void sendMessage(Player player, Functions function, String message) {
        Message messageToSend = new Message.Builder()
                .setUsername(player.userName)
                .setFunction(function)
                .setMessage(message)
                .build();

        player.sendMessage(messageToSend.toString());
    }
}
