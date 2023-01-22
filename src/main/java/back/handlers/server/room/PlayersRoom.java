package back.handlers.server.room;

import back.game.Functions;
import back.game.Round;
import com.example.fortunewheel.Message;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

public class PlayersRoom implements Runnable {
    private final Map<String, PlayerHandler> playerHandlers = new TreeMap();
    private final ExecutorService executorService;
    private final Round[] rounds;

    public PlayersRoom(List<Socket> players, ExecutorService executorService) {
        initializePlayerHandlers(players);
        this.executorService = executorService;
        rounds = new Round[5];
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
        setup();
        while (true) {
            Message messageReceived = Message.convertStringToMessage(playerHandlers.get("a").getMessageFromClient());
            ServerMessageProcessor.processMessage(messageReceived, playerHandlers);
        }
    }

    private void setup() {
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

        for(int i=0;i<rounds.length;i++){
            rounds[i] = new Round();
        }

        messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.SETUP_ROUND)
                .setMessage(rounds[0].getWordAndCategory())
                .build();

        broadcastMessage(messageToSend.toString());

        System.out.println("Sent message about round " + rounds[0].toString());
    }

    private void broadcastMessage(String messageToSend) {
        for (PlayerHandler player : playerHandlers.values()) {
            player.sendMessageToClient(messageToSend);
        }
    }
}
