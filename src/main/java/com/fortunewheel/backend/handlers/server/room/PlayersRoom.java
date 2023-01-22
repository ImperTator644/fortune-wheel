package com.fortunewheel.backend.handlers.server.room;

import com.fortunewheel.backend.game.Functions;
import com.fortunewheel.backend.game.GameFlow;
import com.fortunewheel.backend.game.Round;
import com.fortunewheel.backend.connection.Message;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

public class PlayersRoom implements Runnable {
    private final Map<String, PlayerHandler> playerHandlers = new TreeMap<>();
    private final ExecutorService executorService;
    private final Round[] rounds;
    private final GameFlow gameFlow;

    public PlayersRoom(List<Socket> players, ExecutorService executorService) {
        initializePlayerHandlers(players);
        this.executorService = executorService;
        rounds = new Round[5];
        gameFlow = GameFlow.getInstance();
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

        for(Round round : rounds){
            sendRoundSetup(round);
            gameFlow.playRound(round, playerHandlers);
        }
    }

    private void sendRoundSetup(Round round) {
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.SETUP_ROUND)
                .setMessage(round.getWordAndCategory())
                .build();

        ServerMessageProcessor.processMessageToBroadCast(messageToSend, playerHandlers);

        System.out.println("Sent message about round " + round);
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
        ServerMessageProcessor.processMessageToBroadCast(messageToSend, playerHandlers);

        for(int i=0;i<rounds.length;i++){
            rounds[i] = new Round();
        }
    }
}
