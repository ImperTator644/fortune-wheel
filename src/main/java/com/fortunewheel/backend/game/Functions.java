package com.fortunewheel.backend.game;

public enum Functions {
    LETTER("LETTER"),
    WORD("WORD"),
    SPIN("SPIN"),
    FREE_SPIN("FREE_SPIN"),
    BLOCK("BLOCK"),
    UNBLOCK("UNBLOCK"),
    UNBLOCK_SPIN("UNBLOCK_SPIN"),
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
