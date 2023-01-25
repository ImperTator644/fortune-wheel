package com.fortunewheel.backend.connection;

import com.fortunewheel.backend.game.Functions;
import com.fortunewheel.frontend.PlayerController;
import com.fortunewheel.frontend.WheelSection;
import javafx.application.Platform;

public class PlayerMessageProcessor {
    public static void processMessage(Message message, PlayerController controller) {
        Functions function = message.getFunction();
        switch (function) {
            case SPIN -> {
                WheelSection wheelSection = WheelSection.valueOf(message.getMessage());
                controller.changeWheelView(wheelSection);
            }
            case BLOCK -> Platform.runLater(() -> controller.blockFields());
            case UNBLOCK -> Platform.runLater(() -> controller.unblockFields());
            case UNBLOCK_SPIN -> Platform.runLater(() -> controller.unblockSpin());
            case CHAT -> Platform.runLater(()->controller.changeChat(message.getMessage()));
            case SETUP_ROUND -> Platform.runLater(()->controller.setupRound(message.getMessage()));
            case ROUND_NUMBER -> Platform.runLater(()->controller.setRoundNumber(message.getMessage()));
            case ROUND_PLAYER -> Platform.runLater(()->controller.setCurrentPlayer(message.getMessage()));
            case LETTER -> Platform.runLater(()->controller.uncoverLetters(message.getMessage()));
            case WORD -> Platform.runLater(()->controller.uncoverWord(message.getMessage()));
            case END_ROUND -> Platform.runLater(() -> controller.endRound());
            case MONEY_UPDATE -> Platform.runLater(() ->controller.updateMoney(message.getMessage()));
            case PLAYERS -> Platform.runLater(() -> controller.setPlayers(message.getMessage()));
            case UPDATE_PLAYERS -> Platform.runLater(() -> controller.updatePlayers(message.getMessage()));
        }
    }
}
