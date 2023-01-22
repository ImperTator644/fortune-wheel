package back.game;

import back.handlers.server.room.PlayerHandler;
import back.handlers.server.room.ServerMessageProcessor;
import com.example.fortunewheel.Message;

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
        while(true){
            blockAndUnblockPlayers(players, currentPlayer);
            Message messageReceived = Message.convertStringToMessage(currentPlayer.getMessageFromClient());
            ServerMessageProcessor.processMessageToBroadCast(messageReceived, players);
            iterator = iterator == 2 ? 0 : iterator + 1;
            currentPlayer = playerHandlers.get(iterator);
        }
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
