package com.fortunewheel.backend.handlers.server.room;

import com.fortunewheel.backend.game.Functions;
import com.fortunewheel.backend.connection.Message;
import com.fortunewheel.backend.game.GameFlow;
import com.fortunewheel.frontend.WheelSection;

import java.util.Map;

public class ServerMessageProcessor {
    public static void processMessageToBroadCast(Message message, Map<String, PlayerHandler> playerHandlers, GameFlow gameFlow) {
        Functions function = message.getFunction();
        switch (function) {
            case SPIN -> {
                WheelSection wheelSection = WheelSection.wheelSpin();
                String wheelSectionString = wheelSection.toString();
                Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.SPIN)
                        .setMessage(wheelSectionString)
                        .build();
                broadcastMessage(messageToSend.toString(), playerHandlers);
                gameFlow.setCurrentSection(wheelSection);
            }
            case    SETUP_ROUND,
                    ROUND_NUMBER,
                    ROUND_PLAYER,
                    CHAT,
                    LETTER,
                    END_ROUND,
                    PLAYERS,
                    UPDATE_PLAYERS,
                    WORD-> broadcastMessage(message.toString(), playerHandlers);

        }
    }

    public static void processMessageToOnePlayer(Message message, PlayerHandler playerHandler) {
        Functions function = message.getFunction();
        switch (function) {
            case BLOCK, UNBLOCK, UNBLOCK_SPIN, MONEY_UPDATE -> sendMessageToOnePlayer(message.toString(), playerHandler);
        }
    }

    private static void broadcastMessage(String messageToSend, Map<String, PlayerHandler> playerHandlers) {
        for (PlayerHandler player : playerHandlers.values()) {
            player.sendMessageToClient(messageToSend);
        }
    }

    private static void sendMessageToOnePlayer(String messageToSend, PlayerHandler playerHandler){
        playerHandler.sendMessageToClient(messageToSend);
    }
}
