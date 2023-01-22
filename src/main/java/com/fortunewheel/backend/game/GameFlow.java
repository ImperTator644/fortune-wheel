package com.fortunewheel.backend.game;

import com.fortunewheel.backend.handlers.server.room.PlayerHandler;
import com.fortunewheel.backend.handlers.server.room.ServerMessageProcessor;
import com.fortunewheel.backend.connection.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameFlow {
    private static GameFlow instance = null;

    private GameFlow(){
    }

    public static GameFlow getInstance(){
        if(instance == null){
            instance = new GameFlow();
        }
        return instance;
    }

    public void playRound(Round round, Map<String, PlayerHandler> players){
        List<PlayerHandler> playerHandlers = new ArrayList<>(players.values());
        PlayerHandler currentPlayer = playerHandlers.get(0);
        int iterator = 0;
        sendRoundInfo(players, iterator+1);
        while(true){
            blockAndUnblockPlayers(players, currentPlayer);
            sendCurrentPlayerInfo(players, currentPlayer.getPlayerModel().getUsername());
            Message messageReceived = Message.convertStringToMessage(currentPlayer.getMessageFromClient());
            ServerMessageProcessor.processMessageToBroadCast(messageReceived, players);
            iterator = iterator == 2 ? 0 : iterator + 1;
            currentPlayer = playerHandlers.get(iterator);
        }
    }

    public void sendRoundInfo(Map<String, PlayerHandler> players, int roundNumber){
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.ROUND_NUMBER)
                .setMessage(Integer.toString(roundNumber))
                .build();

        ServerMessageProcessor.processMessageToBroadCast(messageToSend, players);
        System.out.println("Sent info about round =  " + roundNumber);
    }

    public void sendCurrentPlayerInfo(Map<String, PlayerHandler> players, String currentPlayerName){
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.ROUND_PLAYER)
                .setMessage(currentPlayerName)
                .build();

        ServerMessageProcessor.processMessageToBroadCast(messageToSend, players);
        System.out.println("Sent info about current player =  " + currentPlayerName);
    }

    private void blockAndUnblockPlayers(Map<String, PlayerHandler> players, PlayerHandler currentPlayer){
        for(PlayerHandler playerHandler : players.values()){
            if(Objects.equals(playerHandler, currentPlayer)){
                Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.UNBLOCK)
                        .setMessage("null")
                        .build();

                ServerMessageProcessor.processMessageToOnePlayer(messageToSend, playerHandler);

                System.out.println("Unblocked player " + playerHandler);
            }
            else{
                Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.BLOCK)
                        .setMessage("null")
                        .build();

                ServerMessageProcessor.processMessageToOnePlayer(messageToSend, playerHandler);

                System.out.println("Blocked player " + playerHandler);
            }
        }
    }
}
