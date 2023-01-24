package com.fortunewheel.backend.game;

import com.fortunewheel.backend.handlers.server.room.PlayerHandler;
import com.fortunewheel.backend.handlers.server.room.ServerMessageProcessor;
import com.fortunewheel.backend.connection.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameFlow {
    public GameFlow(){}

    public void playRound(Round round, Map<String, PlayerHandler> players){
        List<PlayerHandler> playerHandlers = new ArrayList<>(players.values());
        PlayerHandler currentPlayer = playerHandlers.get(0);
        int iterator = 0;
        String currentWord = round.getWord();
        sendRoundInfo(players, iterator+1);
        while(true){
            blockAndUnblockPlayers(players, currentPlayer);
            sendCurrentPlayerInfo(players, currentPlayer.getPlayerModel().getUsername());
            Message messageReceived = Message.convertStringToMessage(currentPlayer.getMessageFromClient());

            switch (messageReceived.getFunction()){
                case LETTER -> handleGuessedLetter(players, currentPlayer, currentWord, messageReceived);
                case WORD -> handleGuessedWord(players, currentPlayer, currentWord, messageReceived);
                case SPIN -> handleSpin(players, currentPlayer, messageReceived);
            }
            iterator = iterator == 2 ? 0 : iterator + 1;
            currentPlayer = playerHandlers.get(iterator);
        }
    }

    private void handleSpin(Map<String, PlayerHandler> players, PlayerHandler currentPlayer, Message messageReceived) {
        addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.SPUN.getMessageToChat());
        ServerMessageProcessor.processMessageToBroadCast(messageReceived, players);
    }

    private void handleGuessedWord(Map<String, PlayerHandler> players, PlayerHandler currentPlayer, String currentWord, Message messageReceived) {
        addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.GUESSING_WORD.getMessageToChat());

        if(checkIfWordCorrect(messageReceived.getMessage(), currentWord)){
            addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.CORRECT_WORD.getMessageToChat());
            Message messageToSend = new Message.Builder()
                    .setUsername("server")
                    .setFunction(Functions.WORD)
                    .setMessage(messageReceived.getMessage())
                    .build();

            ServerMessageProcessor.processMessageToBroadCast(messageToSend, players);
        }
        else{
            addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.WRONG_WORD.getMessageToChat());
        }
    }

    private void handleGuessedLetter(Map<String, PlayerHandler> players, PlayerHandler currentPlayer, String currentWord, Message messageReceived) {
        addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.GUESSING_LETTER.getMessageToChat());
        if(checkIfLetterCorrect(messageReceived.getMessage(), currentWord)){
            addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.CORRECT_LETTER.getMessageToChat());

            Message messageToSend = new Message.Builder()
                    .setUsername("server")
                    .setFunction(Functions.LETTER)
                    .setMessage(messageReceived.getMessage())
                    .build();

            ServerMessageProcessor.processMessageToBroadCast(messageToSend, players);
        }
        else{
            addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.WRONG_LETTER.getMessageToChat());
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

    private void addMessageToChat(String username, Map<String, PlayerHandler> players, String message){
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.CHAT)
                .setMessage(username + " " + message)
                .build();
        ServerMessageProcessor.processMessageToBroadCast(messageToSend, players);
    }

    private boolean checkIfLetterCorrect(String letter, String wordOfTheRound){
        return wordOfTheRound.contains(letter);
    }

    private boolean checkIfWordCorrect(String guessedWord, String wordOfTheRound){
        return wordOfTheRound.equals(guessedWord);
    }
}
