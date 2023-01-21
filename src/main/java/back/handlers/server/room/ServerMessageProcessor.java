package back.handlers.server.room;

import back.game.Functions;
import com.example.fortunewheel.Message;
import com.example.fortunewheel.WheelSection;

import java.util.Map;

public class ServerMessageProcessor {
    public static void processMessage (Message message, Map<String, PlayerHandler> playerHandlers) {
        Functions function = message.getFunction();
        switch (function) {
            case SPIN -> {
                String wheelSection = WheelSection.wheelSpin().toString();
                Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.SPIN)
                        .setMessage(wheelSection)
                        .build();
                broadcastMessage(messageToSend.toString(), playerHandlers);
            }
        }
    }

    private static void broadcastMessage(String messageToSend, Map<String, PlayerHandler> playerHandlers) {
        for (PlayerHandler player : playerHandlers.values()) {
            player.sendMessageToClient(messageToSend);
        }
    }
}
