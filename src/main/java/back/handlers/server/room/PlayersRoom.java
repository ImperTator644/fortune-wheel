package back.handlers.server.room;

import back.game.Functions;
import com.example.fortunewheel.Message;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

public class PlayersRoom implements Runnable {
    private final Map<String, PlayerHandler> playerHandlers = new TreeMap();
    private final ExecutorService executorService;

    public PlayersRoom(List<Socket> players, ExecutorService executorService) {
        initializePlayerHandlers(players);
        this.executorService = executorService;
    }

    private void initializePlayerHandlers(List<Socket> players) {
        for (Socket socket : players) {
            PlayerHandler playerHandler = new PlayerHandler(socket);
            playerHandlers.put(playerHandler.getPlayerModel().getUsername(), playerHandler);
        }
    }

    @Override
    public void run() {
        System.out.println("Player room initialized");
        for (PlayerHandler playerHandler : playerHandlers.values()) {
            executorService.execute(playerHandler);
            System.out.println("Executor started playerHandler " + playerHandler);
        }
        Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.INFO)
                        .setMessage("Siema witamy w gierce")
                        .build();
        broadcastMessage(messageToSend.toString());
        while (true) {
            Message messageReceived = Message.convertStringToMessage(playerHandlers.get("a").getMessageFromClient());
            ServerMessageProcessor.processMessage(messageReceived, playerHandlers);
            //broadcastMessage(playerHandlers.get("a").getMessageFromClient());
            /*for (PlayerHandler player : playerHandlers.values()) {
                broadcastMessage(player.getMessageFromClient());
            }*/
        }
    }

    private void broadcastMessage(String messageToSend) {
        for (PlayerHandler player : playerHandlers.values()) {
            player.sendMessageToClient(messageToSend);
        }
    }
}
