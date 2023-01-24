package com.fortunewheel.backend.game;

public enum ChatMessages {
    CORRECT_WORD("odgadl haslo"),
    GUESSING_WORD("probuje odgadnac haslo"),
    WRONG_WORD("nie odgadl hasla"),
    CORRECT_LETTER("odgadl litere"),
    GUESSING_LETTER("probuje odgadnac litere"),
    WRONG_LETTER("nie odgadl litery"),
    SPUN("zakrecil kolem");

    private final String messageToChat;

    ChatMessages(String messageToChat){
        this.messageToChat = messageToChat;
    }

    public String getMessageToChat() {
        return messageToChat;
    }
}
