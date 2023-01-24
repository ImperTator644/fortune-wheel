package com.fortunewheel.backend.game;

public enum Functions {
    LETTER("LETTER"),
    WORD("WORD"),
    SPIN("SPIN"),
    BLOCK("BLOCK"),
    UNBLOCK("UNBLOCK"),
    SETUP_ROUND("SETUP_ROUND"),
    ROUND_NUMBER("ROUND_NUMBER"),
    ROUND_PLAYER("ROUND_PLAYER"),
    INFO("INFO"),
    CHAT("CHAT");

    private final String function;

    Functions(String function){
        this.function = function;
    }

    public String getFunction() {
        return function;
    }
}
