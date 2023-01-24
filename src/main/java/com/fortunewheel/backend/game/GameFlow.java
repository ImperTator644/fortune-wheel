package com.fortunewheel.backend.game;

import com.fortunewheel.backend.handlers.server.room.PlayerHandler;
import com.fortunewheel.backend.handlers.server.room.ServerMessageProcessor;
import com.fortunewheel.backend.connection.Message;
import com.fortunewheel.frontend.WheelSection;

import java.util.*;

public class GameFlow {

    private Map<WheelSection, String> wheelValuesMap;
    private WheelSection currentSection;
    private PlayerHandler currentPlayer;
    private int iterator;
    private List<PlayerHandler> playerHandlers;
    private Map<String, PlayerHandler> players;
    private boolean playRound;

    public WheelSection getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(WheelSection currentSection) {
        System.out.println("Current Section: " + currentSection);
        this.currentSection = currentSection;
    }


    public GameFlow(){
        initializeMap();
    }

    private void initializeMap() {
        wheelValuesMap = new HashMap<>();
        wheelValuesMap.put(WheelSection.WHEEL_300_1, "300");
        wheelValuesMap.put(WheelSection.WHEEL_300_2, "300");
        wheelValuesMap.put(WheelSection.WHEEL_300_3, "300");
        wheelValuesMap.put(WheelSection.WHEEL_300_4, "300");
        wheelValuesMap.put(WheelSection.WHEEL_300_5, "300");
        wheelValuesMap.put(WheelSection.WHEEL_350, "350");
        wheelValuesMap.put(WheelSection.WHEEL_400_1, "400");
        wheelValuesMap.put(WheelSection.WHEEL_400_2, "400");
        wheelValuesMap.put(WheelSection.WHEEL_450, "450");
        wheelValuesMap.put(WheelSection.WHEEL_500_1, "500");
        wheelValuesMap.put(WheelSection.WHEEL_500_2, "500");
        wheelValuesMap.put(WheelSection.WHEEL_550, "550");
        wheelValuesMap.put(WheelSection.WHEEL_600_1, "600");
        wheelValuesMap.put(WheelSection.WHEEL_600_2, "600");
        wheelValuesMap.put(WheelSection.WHEEL_600_3, "600");
        wheelValuesMap.put(WheelSection.WHEEL_700, "700");
        wheelValuesMap.put(WheelSection.WHEEL_800, "800");
        wheelValuesMap.put(WheelSection.WHEEL_900_1, "900");
        wheelValuesMap.put(WheelSection.WHEEL_900_2, "900");
        wheelValuesMap.put(WheelSection.WHEEL_2500, "2500");
        wheelValuesMap.put(WheelSection.WHEEL_BANKRUPT, "BANKRUPT");
        wheelValuesMap.put(WheelSection.WHEEL_BANKRUPT_1000, "BANKRUPT_1000");
        wheelValuesMap.put(WheelSection.WHEEL_FREE_SPIN, "FREE_SPIN");
        wheelValuesMap.put(WheelSection.WHEEL_LOSE_TURN, "LOSE_TURN");
    }

    public void playRound(Round round, Map<String, PlayerHandler> players, int roundNumber){
        playRound = true;
        this.players = players;
        playerHandlers = new ArrayList<>(players.values());
        currentPlayer = playerHandlers.get(0);
        iterator = 0;
        blockAndUnblockPlayers(players, currentPlayer);
        String currentWord = round.getWord();
        sendRoundInfo(players, roundNumber);
        resetPlayerInRoundMoney(players);
        while(playRound){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sendCurrentPlayerInfo(players, currentPlayer.getPlayerModel().getUsername());
            Message messageReceived = Message.convertStringToMessage(currentPlayer.getMessageFromClient());
            switch (messageReceived.getFunction()){
                case LETTER -> handleGuessedLetter(players, currentPlayer, currentWord, messageReceived);
                case WORD -> handleGuessedWord(players, currentPlayer, currentWord, messageReceived);
                case SPIN -> handleSpin(players, currentPlayer, messageReceived);
            }
        }
    }

    private void changePlayer() {
        iterator = iterator == 2 ? 0 : iterator + 1;
        currentPlayer = playerHandlers.get(iterator);
        blockAndUnblockPlayers(players, currentPlayer);
    }

    private void unblockAllFunctionsForPlayer(PlayerHandler currentPlayer) {
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.UNBLOCK)
                .setMessage("null")
                .build();

        ServerMessageProcessor.processMessageToOnePlayer(messageToSend, currentPlayer);

        System.out.println("Unblocked player " + currentPlayer);
    }

    private void processWheelSection(PlayerHandler currentPlayer) {
        switch (currentSection) {
            case WHEEL_BANKRUPT -> {
                currentPlayer.getPlayerModel().resetRoundMoney();
                updateMoney(currentPlayer);
                changePlayer();
            }
            case WHEEL_BANKRUPT_1000 -> {
                currentPlayer.getPlayerModel().updatePlayerRoundMoney(-1000);
                updateMoney(currentPlayer);
                changePlayer();
            }
            case WHEEL_FREE_SPIN -> {
                Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.FREE_SPIN)
                        .setMessage("null")
                        .build();
                ServerMessageProcessor.processMessageToOnePlayer(messageToSend, currentPlayer);
            }
            case WHEEL_LOSE_TURN -> {
                changePlayer();
            }
            default -> {
                unblockAllFunctionsForPlayer(currentPlayer);
            }
        }
    }

    private void updateMoney(PlayerHandler currentPlayer) {
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.MONEY_UPDATE)
                .setMessage(String.valueOf(currentPlayer.getPlayerModel().getMoneyInRound()))
                .build();


        ServerMessageProcessor.processMessageToOnePlayer(messageToSend, currentPlayer);
    }

    private void handleSpin(Map<String, PlayerHandler> players, PlayerHandler currentPlayer, Message messageReceived) {
        addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.SPUN.getMessageToChat());
        ServerMessageProcessor.processMessageToBroadCast(messageReceived, players, this);

        processWheelSection(currentPlayer);
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

            currentPlayer.getPlayerModel().updatePlayerRoundMoney(Integer.valueOf(wheelValuesMap.get(currentSection)));
            ServerMessageProcessor.processMessageToBroadCast(messageToSend, players, this);
            playRound = false;
            currentPlayer.getPlayerModel().updatePlayerMoney(currentPlayer.getPlayerModel().getMoneyInRound());
            messageToSend = new Message.Builder()
                    .setUsername("server")
                    .setFunction(Functions.END_ROUND)
                    .setMessage("null")
                    .build();
            ServerMessageProcessor.processMessageToBroadCast(messageToSend, players, this);
        }
        else{
            addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.WRONG_WORD.getMessageToChat());
            changePlayer();
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

            currentPlayer.getPlayerModel().updatePlayerRoundMoney(Integer.valueOf(wheelValuesMap.get(currentSection)));
            ServerMessageProcessor.processMessageToBroadCast(messageToSend, players, this);

            updateMoney(currentPlayer);

            blockAndUnblockPlayers(players, currentPlayer);
        }
        else{
            addMessageToChat(currentPlayer.getPlayerModel().getUsername(), players, ChatMessages.WRONG_LETTER.getMessageToChat());
            changePlayer();
        }
    }

    public void sendRoundInfo(Map<String, PlayerHandler> players, int roundNumber){
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.ROUND_NUMBER)
                .setMessage(Integer.toString(roundNumber))
                .build();

        ServerMessageProcessor.processMessageToBroadCast(messageToSend, players, this);
        System.out.println("Sent info about round =  " + roundNumber);
    }

    private void resetPlayerInRoundMoney(Map<String, PlayerHandler> players) {
        for (PlayerHandler playerHandler : players.values()) {
            playerHandler.getPlayerModel().resetRoundMoney();
            updateMoney(playerHandler);
        }
    }

    public void sendCurrentPlayerInfo(Map<String, PlayerHandler> players, String currentPlayerName){
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.ROUND_PLAYER)
                .setMessage(currentPlayerName)
                .build();

        ServerMessageProcessor.processMessageToBroadCast(messageToSend, players, this);
        System.out.println("Sent info about current player =  " + currentPlayerName);
    }

    private void blockAndUnblockPlayers(Map<String, PlayerHandler> players, PlayerHandler currentPlayer){
        for(PlayerHandler playerHandler : players.values()){
            if(Objects.equals(playerHandler, currentPlayer)){
                Message messageToSend = new Message.Builder()
                        .setUsername("server")
                        .setFunction(Functions.UNBLOCK_SPIN)
                        .setMessage("null")
                        .build();

                ServerMessageProcessor.processMessageToOnePlayer(messageToSend, playerHandler);

                System.out.println("Unblocked spin for player " + playerHandler);
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

    public void addMessageToChat(String username, Map<String, PlayerHandler> players, String message){
        Message messageToSend = new Message.Builder()
                .setUsername("server")
                .setFunction(Functions.CHAT)
                .setMessage(username + " " + message)
                .build();
        ServerMessageProcessor.processMessageToBroadCast(messageToSend, players, this);
    }

    private boolean checkIfLetterCorrect(String letter, String wordOfTheRound){
        return wordOfTheRound.contains(letter);
    }

    private boolean checkIfWordCorrect(String guessedWord, String wordOfTheRound){
        return wordOfTheRound.equals(guessedWord);
    }
}
